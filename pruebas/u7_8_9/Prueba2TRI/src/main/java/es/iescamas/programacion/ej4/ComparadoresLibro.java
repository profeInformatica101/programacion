package es.iescamas.programacion.ej4;

import java.util.Comparator;

public class ComparadoresLibro {

	static final Comparator<Libro> COMPARADOR_ANYO = Comparator.comparing(Libro::getAnyo);
	static final Comparator<Libro> COMPARADOR_PRECIO = Comparator.comparing(Libro::getPrecio);
}
