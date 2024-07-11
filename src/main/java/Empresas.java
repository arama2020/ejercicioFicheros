import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="empresas")
public class Empresas {

	private ArrayList<Empresa> empresa;

	public Empresas() {
	}

	public Empresas(ArrayList<Empresa> empresa) {
		super();
		this.empresa = empresa;
	}

	//@XmlElementWrapper(name = "ventas")
	@XmlElement(name = "empresa")
	public ArrayList<Empresa> getEmpresa() {
		return empresa;
	}

	public void setEmpresa(ArrayList<Empresa> empresa) {
		this.empresa = empresa;
	}
}
