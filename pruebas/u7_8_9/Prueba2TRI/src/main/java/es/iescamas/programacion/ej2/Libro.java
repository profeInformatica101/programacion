package es.iescamas.programacion.ej2;

public class Libro implements Catalogable{
	private String titulo;
	private String autor;
	private int anyo;
	private double precio;
	
	public Libro(String tiulo, String autor, int anyo, double precio) {
		super();
		this.titulo = tiulo;
		this.autor = autor;
		this.anyo = anyo;
		this.precio = precio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String tiulo) {
		this.titulo = tiulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public void mostrarFicha() {
		System.out.printf("[%d] %s — %s (%.2f €)", anyo, titulo, autor, precio);
	}

	@Override
	public String getCodigo() {
		StringBuilder sb = new StringBuilder("LIB-");
		sb.append(anyo);
		sb.append(titulo.substring(0, 2).toUpperCase());
		return sb.toString();
	}

	@Override
	public void mostrarEnCatalogo() {
		mostrarFicha();
		System.out.printf("[%s]",getCodigo());
	}
	
}
