import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "nombre", "salario", "puesto" })
public class Emple {

	private String nombre;
	private float salario;
	private String puesto;

	public Emple() {
	}

	public Emple(String nombre, float salario, String puesto) {
		super();
		this.nombre = nombre;
		this.salario = salario;
		this.puesto = puesto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getSalario() {
		return salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

}
