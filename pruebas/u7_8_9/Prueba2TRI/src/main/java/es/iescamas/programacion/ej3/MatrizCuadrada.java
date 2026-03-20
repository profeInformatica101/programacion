package es.iescamas.programacion.ej3;

public class MatrizCuadrada extends MatrizDatos {

	public MatrizCuadrada(int[][] datos) {
		super(datos);
	}
	
	@Override
	public void mostrarResultado() {
		super.mostrarResultado();
		if(super.getDatos().length == super.getDatos()[0].length) {
			System.out.printf("Matriz cuadrada: %d x %d", super.getDatos().length, super.getDatos().length);
		}
	}

}
