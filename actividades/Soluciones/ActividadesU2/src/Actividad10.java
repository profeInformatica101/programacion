
import java.util.Scanner;
/**
 *  Pedir un número entre 0 y 9.999, y decir si es capicúa.
 */
public class Actividad10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        /**
         * ################################
         * #       LITERALES              #
         * ################################
         */
        final String  LITERAL_ES_CAPICUA = " es capicúa.";
        final String  NEGACION = "no";
        
        // PRUEBA mostrar por consola el total de argumentos del programa
        System.out.println("Total argumentos: " + args.length);
        ///////   fin prueba ///////////
        
        /**
         * #################################################
         * #       Solicito al usuario un número           #
         * #################################################
         */

        System.out.print("Introduce un número (2-4 cifras): ");
        int numero = sc.nextInt();
        /**
         *
         * ################################################
         * #   Solo un dígito.    X  <--- no es capicúa.  #
         * ################################################
         * 
         *  Todo número de un solo dígito se considera capicúa por definición."
         */
        if (numero < 10) {
            System.out.println("No es capicúa (solo 1 cifra).");
        }
        /**
        *
        * ################################################
        * #   Dos dígitos.                               #
        * #   Es capicúa si ambas cifras son iguales.    #
        * #   Ejemplo: 44 ✓ |  52 ✗ | 11 ✓ |  23 ✗ |     #
        * ################################################
        */
        else if (numero < 100) { // 2 cifras
            int unidades = numero % 10;
            int decenas = numero / 10;

            if (unidades == decenas) {
                System.out.println(numero + LITERAL_ES_CAPICUA);
            } else {
                System.out.println(numero + " " + NEGACION + LITERAL_ES_CAPICUA);
            }
        }
        /**
        *
        * ################################################
        * #   Tres dígitos.                              #
        * #   Es capicúa si la primera y la última son   #
        * #   iguales.                                   #
        * #   Ejemplo: 121 ✓ |  123 ✗ |  444 ✓ |  472 ✗  #
        * ################################################
        */
        else if (numero < 1000) { // 3 cifras
            int unidades = numero % 10;
            int centenas = numero / 100;

            if (unidades == centenas) {
                System.out.println(numero + LITERAL_ES_CAPICUA);
            } else {
                System.out.println(numero + " " + NEGACION + LITERAL_ES_CAPICUA);
            }
        }
        /**
        *
        * ################################################
        * #   Cuatro dígitos.                            #
        * #   Es capicúa si las dos primeras cifras      #
        * #   coinciden con las dos últimas en orden     #
        * #   inverso.                                   #
        * #   Ejemplo: 1221 ✓ |  3443 ✓ |  1234 ✗        #
        * ################################################
        */
        else if (numero < 10000) { // 4 cifras
            int unidades = numero % 10;
            int decenas = (numero / 10) % 10;
            int centenas = (numero / 100) % 10;
            int millares = numero / 1000;

            if (unidades == millares && decenas == centenas) {
                System.out.println(numero + LITERAL_ES_CAPICUA);
            } else {
                System.out.println(numero + " " + NEGACION + LITERAL_ES_CAPICUA);
            }
        }
        else {
            System.out.println("Número demasiado grande para este ejemplo.");
        }
    }
}
