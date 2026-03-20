package es.iescamas.programacion.ej3;

public class Main {
	public static void main(String [] args) {
	
			
			int[][] datos1 = {
				{1, 2},
				{3, 4}
			};

			int[][] datos2 = {
				{5, 6},
				{7, 8}
			};

			OperableMatriz[] matrices = new OperableMatriz[2];
			matrices[0] = new MatrizDatos(datos1);
			matrices[1] = new MatrizCuadrada(datos2);

			// Polimorfismo: el array de tipo OperableMatriz puede almacenar objetos
			// de distintas clases y cada uno ejecuta su propia versión de mostrarResultado().
			for (OperableMatriz matriz : matrices) {
				matriz.mostrarResultado();
				System.out.println();
			}
		
	}
}