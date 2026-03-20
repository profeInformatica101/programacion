package es.iescamas.programacion.ej1;

public class Main {
	
	
	public static void main(String [] args) {
		Libro libro1 = new Libro("El Quijote", "Miguel de Cervantes", 1605, 19.95);
		Libro libro2 = new Libro("Hamlet", "William Shakespeare", 1605, 15.75);
		Libro libro3 = new Libro("Clean Code", "Robert C. Martin", 2008, 32.99);
		
		libro1.mostrarFicha();
		libro2.mostrarFicha();
		libro3.mostrarFicha();
		
		/**
		 * Apartado c)
		 * */
		comparaLibros(libro1, libro2);
		comparaLibros(libro2, libro3);
		/**
		 * Apartado d)
		 */
		aplicaDescuento(libro1);
	}
	
	public static void comparaLibros(Libro l1, Libro l2) {
		if(l1.getAnyo() < l2.getAnyo()) {
			System.out.printf("El libro %s (%d) es el más reciente", l2.getTitulo(), l2.getAnyo() );
		}else if(l1.getAnyo() > l2.getAnyo()) {
			System.out.printf("El libro %s (%d) es el más reciente", l1.getTitulo(), l1.getAnyo() );
		}else {
			System.out.println("Misma edición");
		}
	}
	
	public static void aplicaDescuento(Libro l) {
		double[] descuentos = {0.05, 0.10, 0.15, 0.20};
		double precio = l.getPrecio();

		l.mostrarFicha();
		System.out.println("Descuentos:");

		for (double des : descuentos) {
			double precioFinal = precio * (1 - des);
			System.out.printf("Aplicando el descuento del %.0f%%, el precio final es %.2f €%n",
					des * 100, precioFinal);
		}
	}
	
}
