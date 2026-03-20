package es.iescamas.programacion.ej3;

import java.util.Arrays;

public class MatrizDatos  implements OperableMatriz{
	private int[][] datos;
	
	
	public MatrizDatos(int[][] datos) {
		
		if (datos == null) {
			throw new IllegalArgumentException("La matriz no puede ser null.");
		}
	
		this.datos = datos;
	}
	
	

	public int[][] getDatos() {
		return datos;
	}


	public void setDatos(int[][] datos) {
		this.datos = datos;
	}

	@Override
	public int[][] sumar(int[][] otra) {
		if (otra == null) {
			throw new IllegalArgumentException("La matriz no puede ser null.");
		}

		if (datos.length != otra.length) {
			throw new IllegalArgumentException("Las matrices no tienen las mismas dimensiones.");
		}

		for (int i = 0; i < datos.length; i++) {
			if (datos[i].length != otra[i].length) {
				throw new IllegalArgumentException("Las matrices no tienen las mismas dimensiones.");
			}
		}

		int[][] resultado = new int[datos.length][datos[0].length];

		for (int i = 0; i < datos.length; i++) {
			for (int j = 0; j < datos[i].length; j++) {
				resultado[i][j] = datos[i][j] + otra[i][j];
			}
		}

		this.datos = resultado; 
		return resultado;       
	}

	@Override
	public void mostrarResultado() {
		System.out.println(Arrays.deepToString(datos));
		
	} 
	
}
