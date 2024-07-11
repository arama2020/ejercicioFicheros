import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "codempre", "direccion", "nuevodirector", "nuevosemples" })
public class Empresa {

	 private int codempre;
	 private String direccion;
	 private Emple nuevodirector;
	 private ArrayList<Emple> nuevosemples;
	 
	 public Empresa() {}
	 
	public Empresa(int codempre, String direccion, Emple nuevodirector, ArrayList<Emple> nuevosemples) {
		super();
		this.codempre = codempre;
		this.direccion = direccion;
		this.nuevodirector = nuevodirector;
		this.nuevosemples = nuevosemples;
	}
	public int getCodempre() {
		return codempre;
	}
	public void setCodempre(int codempre) {
		this.codempre = codempre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Emple getNuevodirector() {
		return nuevodirector;
	}
	public void setNuevodirector(Emple nuevodirector) {
		this.nuevodirector = nuevodirector;
	}
	
	@XmlElementWrapper(name = "nuevosemples")
	@XmlElement(name = "emple")
	public ArrayList<Emple> getNuevosemples() {
		return nuevosemples;
	}
	public void setNuevosemples(ArrayList<Emple> nuevosemples) {
		this.nuevosemples = nuevosemples;
	}

			
}
