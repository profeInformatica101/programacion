package com;

import java.util.Scanner;

/**
 * Actividad 7 Pedir tres números y mostrarlos ordenados de mayor a menor.
 */
public class Actividad7c {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Definimos las variables
		double num1, num2, num3;
		double mayor, medio, menor;
	
		System.out.println("Dime un número");
		num1 = sc.nextDouble();
		System.out.println("Dime otro número");
		num2 = sc.nextDouble();
		System.out.println("Dime otro número");
		num3 = sc.nextDouble();
		
		
		if((num1 > num2) && (num2 > num3) && (num1 > num3)) {
			mayor = num1;
			medio = num2;
			menor = num3;
			System.out.println("Sus números ordenados serían "+ mayor + ">" + medio + ">" + menor);
		}else if((num2 > num1) && (num2 > num3) && (num3 > num1)) {
			mayor = num2;
			medio = num3;
			menor = num1;
			System.out.println("Sus números ordenados serían "+ mayor + ">" + medio + ">" + menor);
		}
	
	}
	
}
