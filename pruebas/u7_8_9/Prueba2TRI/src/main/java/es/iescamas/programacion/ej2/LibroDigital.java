package es.iescamas.programacion.ej2;

public class LibroDigital extends Libro{

	private String formato;
	
	public LibroDigital(String tiulo, String autor, int anyo, double precio, String formato) {
		super(tiulo, autor, anyo, precio);
		this.formato = formato;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	@Override
	public void mostrarEnCatalogo() {
		super.mostrarEnCatalogo();
		System.out.printf("[%s]", formato);
		
	}
}
