import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.util.Scanner;

public class Principal {
	static int LON = 192;
	static String ficherodirecto = "Empresas.dat";
	static String ficheroxml = "empresas.xml";

	public static void main(String[] args) {

		
		Scanner sc = new Scanner(System.in);
		int opcion = 0;

		do {
			menu();
			for (;;) {
				try {
					System.out.println("TECLEA OPCIÓN (1 a 5):");
					opcion = sc.nextInt();
					if (opcion > 0 && opcion < 6) {
						break;
					}

				} catch (java.util.InputMismatchException e) {
					sc.nextLine();
				}
			}

			switch (opcion) {
			case 1:
				visualizarxml();
				break;
			case 2:
				visualizarfichero(ficherodirecto);
				break;
			case 3:
				cargarxmlyactualizar();
				break;
			case 4:
				crearfichero(ficherodirecto);
				break;
			case 5:
				System.out.println("----------------------");
				System.out.println("\tFIN");
				System.out.println("----------------------");
				break;

			default:
				System.out.println("Opcion no válida, prueba otra vez");
				break;
			}
		} while (opcion != 5);

	}
		
		
	
	private static void menu() {
		System.out.println("------------------------------------------------------------");
		System.out.println("OPERACIONES CON EMPRESAS");
		System.out.println("");
		System.out.println("\t1. EJERCICIO 1: Mostrar el XML empresas.xml");
		System.out.println("\t2. EJERCICIO 2: Listar las empresas del fichero directo");
		System.out.println("\t3. EJERCICIO 3: Actualizar empresas.dat");
		System.out.println("\t4. EJERCICIO 4: Crear fichero directo");
		System.out.println("\t5. Salir");
		System.out.println("------------------------------------------------------------");

	}
	
	private static void visualizarxml() {
		
		JAXBContext context;
		
		try {
			context = JAXBContext.newInstance(Empresas.class);
			Unmarshaller unmars = context.createUnmarshaller();

			try {
				Empresas empre = (Empresas) unmars.unmarshal(new FileReader(ficheroxml));
				ArrayList<Empresa> lista = empre.getEmpresa();
				System.out.println("CONTENIDO XML. Número de empresas: " + lista.size());
				System.out.println("------------------------------------- " );
				for (Empresa emp : lista) {
					// Sacamos la empresa
					int code = emp.getCodempre();
					String direccion = emp.getDireccion();
					String director = emp.getNuevodirector().getNombre();
    
					System.out.println("\nCOD EMPRESA: " + code + ". Dirección: "+direccion);
					System.out.println("Nombre director: "+ director + ", Salario: "+emp.getNuevodirector().getSalario());
					
					// sacamos los nuevos emples
					ArrayList<Emple> listaem = emp.getNuevosemples();
					int num = listaem.size();
					float totsal = 0;
					System.out.println("\tLista de empleados.");
					
					System.out.printf("%10s %-30s %13s %-30s%n","", "Nombre", "Salario", "Puesto");
					System.out.printf("%10s %-30s %13s %-30s%n","", "------------------------------", "----------", "--------------------");
					
					for (Emple eemp : listaem) {
						float sal = eemp.getSalario();
						totsal = totsal + sal;
						
						System.out.printf("%10s %-30s %13s %-30s%n"," ", eemp.getNombre(), 
								eemp.getSalario(), eemp.getPuesto());
						
						
					} // fin for
					float media = 0;
					if (num > 0) {
						media = totsal / num;
					}

					System.out.printf("%10s %-30s %13s %-30s%n", "","------------------------------", "----------", "--------------------");
					

				}
				

			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				// e.printStackTrace();
			}

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void cargarxmlyactualizar() {

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Empresas.class);
			Unmarshaller unmars = context.createUnmarshaller();

			try {
				Empresas empre = (Empresas) unmars.unmarshal(new FileReader(ficheroxml));
				ArrayList<Empresa> lista = empre.getEmpresa();
				System.out.println("Número de Empresas: " + lista.size());
				for (Empresa emp : lista) {
					// Sacamos la empresa
					int code = emp.getCodempre();
					String direccion = emp.getDireccion();
					String director = emp.getNuevodirector().getNombre();

					// sacamos los nuevos emples
					ArrayList<Emple> listaem = emp.getNuevosemples();
					int num = listaem.size();
					float totsal = 0;

					for (Emple eemp : listaem) {
						float sal = eemp.getSalario();
						totsal = totsal + sal;
					} // fin for
					float media = 0;
					if (num > 0) {
						media = totsal / num;
					}

					System.out.println("Datos leídos: ");
					System.out.println("Cod: " + code + ", direccion: " + direccion + ", director: " + director
							+ ", num emples = " + num + ", media: " + media);

					// Actualizar el directo
					actualizardirecto(code, direccion, num, director, media);

				}
				// articulo

			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				// e.printStackTrace();
			}

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void actualizardirecto(int code, String direccion, int num, String director, float media) {

		File fichero = new File(ficherodirecto);
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			long posi = (code - 1) * LON;
			if (posi < file.length()) {
				file.seek(posi);
				int cod = file.readInt();
				if (cod == code) { // se actualiza

					// actualiza direccion
					file.seek(posi + 64);
					StringBuffer buffer = null;
					buffer = new StringBuffer(direccion);
					buffer.setLength(30);
					file.writeChars(buffer.toString());

					// Actualizo num emple, los leo y los sumo
					file.seek(posi + 124);
					int nuu = file.readInt();
					int emples = num + nuu;
					file.seek(posi + 124);
					file.writeInt(emples);

					// actualizo el salario
					file.seek(posi + 128);
					float sall = file.readFloat();
					float medianue = (sall + media) / 2;
					file.seek(posi + 128);
					file.writeFloat(medianue);

					// Actualizo director
					file.seek(posi + 132);
					buffer = null;
					buffer = new StringBuffer(director);
					buffer.setLength(30);
					file.writeChars(buffer.toString());

					System.out.println("Se actualiza el " + code);

				} else {
					System.out.println("No se localiza es hueco. Se añade: " + code);
					//SE CREA NUEVA EMPRESA
					posi = (code - 1) * LON;
					file.seek(posi);

					file.writeInt(code);

					StringBuffer buffer = null;
					buffer = new StringBuffer("NUEVA EMPRESA");
					buffer.setLength(30);
					file.writeChars(buffer.toString());

					buffer = null;
					buffer = new StringBuffer(direccion);
					buffer.setLength(30);
					file.writeChars(buffer.toString());

					file.writeInt(num);

					file.writeFloat(media);

					buffer = null;
					buffer = new StringBuffer(director);
					buffer.setLength(30);
					file.writeChars(buffer.toString());

				}
			} else {
				System.out.println("No se localiza, sobrepasa, se añade: " + code);
				//SE CREA NUEVA EMPRESA
				posi = (code - 1) * LON;
				file.seek(posi);

				file.writeInt(code);

				StringBuffer buffer = null;
				buffer = new StringBuffer("NUEVA EMPRESA");
				buffer.setLength(30);
				file.writeChars(buffer.toString());

				buffer = null;
				buffer = new StringBuffer(direccion);
				buffer.setLength(30);
				file.writeChars(buffer.toString());

				file.writeInt(num);

				file.writeFloat(media);

				buffer = null;
				buffer = new StringBuffer(director);
				buffer.setLength(30);
				file.writeChars(buffer.toString());
			}
			System.out.println("--------------------------");
			
			file.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	private static void visualizarfichero(String nombre) {
		File fichero = new File(nombre);
		System.out.println("--------------------------");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "r");
			long posi = 0;
			System.out.printf("%3s %-30s %-30s %9s %13s %-30s%n", "COD", "NOMBRE EMPRESA", "DIRECCIÓN EMPRESA",
					"NUMEMPLES", "MEDIA SALARIO", "NOMBRE DIRECTOR");
			System.out.printf("%3s %-30s %-30s %9s %13s %-30s%n", "---", "------------------------------",
					"------------------------------", "---------", "-------------", "------------------------------");
			for (;;) {
				String nombreempresa = "";
				String direccion = "";

				file.seek(posi);
				int cod = file.readInt();
				if (cod > 0) {

					char cad;
					for (int i = 0; i < 30; i++) {
						cad = file.readChar();
						nombreempresa = nombreempresa + cad;
					}

					for (int i = 0; i < 30; i++) {
						cad = file.readChar();
						direccion = direccion + cad;
					}

					int numemple = file.readInt();
					float mediasal = file.readFloat();

					cad = 0;
					String director = "";
					for (int i = 0; i < 30; i++) {
						cad = file.readChar();
						// System.out.printf("%2s ",cad);
						director = director + cad;
					}

					System.out.printf("%3s %-30s %-30s %9s %13s %30s%n", cod, nombreempresa, direccion, numemple,
							mediasal, director);

				}

				posi = posi + LON;
				if (file.getFilePointer() == file.length() || posi >= file.length())
					break;

			}

			System.out.printf("%3s %-30s %-30s %9s %13s %-30s%n", "---", "------------------------------",
					"------------------------------", "---------", "-------------", "------------------------------");
			file.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static void crearfichero(String nombre) {
		File fichero = new File(nombre);
		fichero.delete();
		fichero = new File(nombre);
		System.out.println("INSERCIÓN DE DATOS");

		try {
			// declara el fichero de acceso aleatorio
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");

			// arrays con los datos
			int cod[] = { 1, 3, 5, 10, 11, 12, 16 };
			String nombreempresa[] = { "Legalitas S.L.", "Suministros Juan", "Lopez y CIA", "Ganadería Ramos",
					"Papelerías reunidas", "S.A. La Alameda", "S.A. Almacenes REY" };
			String direccion[] = { "C/Alta 3. Talavera", "C/Mayor 31. Talavera", "C/Avenidas 10. Toledo",
					"Avda Espartales 4. Madrid", "Polígono 12-A. Toledo.", "Polígono 102. Madrid",
					"C/Carpinteros 4. Talavera" }; 

			String director[] = { "Antonio Fernández", "Juan Gil", "Sebastián López", "Ana Ramos", "Pedro Sevilla",
					"María Casilla", "Antonio Rey" };

			int numemple[] = { 4, 3, 5, 10, 11, 8, 10 };

			float mediasal[] = { 1000.45f, 2400.60f, 3000.0f, 1570.56f, 2000.10f, 1435.87f, 2000.0f };// salarios

			StringBuffer buffer = null;// buffer para almacenar cadenas

			int n = cod.length;// numero de elementos del array 7
			long posi = 0;

			for (int i = 0; i < n; i++) { // recorro los arrays

				posi = (cod[i] - 1) * LON;
				file.seek(posi);

				file.writeInt(cod[i]);

				buffer = null;
				buffer = new StringBuffer(nombreempresa[i]);
				buffer.setLength(30);
				file.writeChars(buffer.toString());

				buffer = null;
				buffer = new StringBuffer(direccion[i]);
				buffer.setLength(30);
				file.writeChars(buffer.toString());

				file.writeInt(numemple[i]);

				file.writeFloat(mediasal[i]);// insertar salario

				buffer = null;
				buffer = new StringBuffer(director[i]);
				buffer.setLength(30);
				file.writeChars(buffer.toString());

				System.out.println("Reg insertado: " + cod[i] + " * " + nombreempresa[i] + ", director: "
						+ buffer.toString() + "**");
			}
			file.close(); // cerrar fichero

		} catch (IOException e) {
			e.printStackTrace();

		}

	}
}
