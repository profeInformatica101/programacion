package es.iescamas.programacion.ej4;

import java.util.Arrays;

public class Main {
	/**
	 *  Comparable define el orden natural de la clase desde dentro de la propia clase
	 *  mediante compareTo(), mientras que Comparator permite crear criterios de
	 *  ordenación externos e independientes, como ordenar por año o por precio
	 */
	public static void main(String [] args) {
		Libro[] libros = new Libro[4];
		libros[0] = new Libro("El Quijote", "Miguel de Cervantes", 1605, 19.95);
		libros[1] = new Libro("1984", "George Orwell", 1949, 14.50);
		libros[2] = new Libro("Clean Code", "Robert C. Martin", 2008, 32.99);
		libros[3] = new Libro("Hamlet", "William Shakespeare", 1603, 15.75);
		
		// Orden natural: por título
		Arrays.sort(libros);
		System.out.println("Ordenados por título:");
		mostrarLibros(libros);
		
		Arrays.sort(libros, new ComparadorPorAnyo());
		System.out.println("Ordenados por año:");
		mostrarLibros(libros);
		
		Arrays.sort(libros, ComparadoresLibro.COMPARADOR_PRECIO);
		System.out.println("Ordenados por precio:");
		mostrarLibros(libros);

	}
	
	public static void mostrarLibros(Libro[] libros) {
		for (Libro libro : libros) {
			libro.mostrarFicha();
			System.out.println();
		}
	}
	
}
