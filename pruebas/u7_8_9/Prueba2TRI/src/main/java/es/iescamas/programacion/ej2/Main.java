package es.iescamas.programacion.ej2;

import es.iescamas.programacion.ej2.Libro;

public class Main {

	/**
	 * OOP :: Object-oriented programming 
	 * 
	 * Herencia → LibroDigital extends Libro
	 * Interfaces → Libro implements Catalogable
	 * Polimorfismo → el array Catalogable[]
	 * 
	 *  Polimorfismo: distintos objetos (Libro y LibroDigital) se tratan
	 *  como Catalogable y cada uno ejecuta su propia versión de mostrarEnCatalogo().
	 */
	public static void main(String[] args) {
		Libro l1 = new Libro("Hamlet", "William Shakespeare", 1605, 15.75);
		LibroDigital l2 = new LibroDigital("Clean Code", "Robert C. Martin", 2008, 32.99, "EPUB");

		Catalogable[] catalogables = {l1, l2};
		for(Catalogable c : catalogables ) {
			c.mostrarEnCatalogo();
			System.out.println();
		}
	}

}
