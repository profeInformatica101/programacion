package es.dni;
import java.util.concurrent.ThreadLocalRandom;

public class ControladorDNI {

    // Letras oficiales del DNI (posición = número % 23)
    public static final char[] LETRAS = {
            'T','R','W','A','G','M','Y','F','P','D','X','B',
            'N','J','Z','S','Q','V','H','L','C','K','E'
    };

    public static final int DIVISOR_DNI = 23;
    public static final int LONGITUD_NUMERO_DNI = 8;
    public static final int LONGITUD_DNI_COMPLETO = 9;

    public char calcularLetra(String numeroDni) {
        if (numeroDni == null) {
            throw new IllegalArgumentException("El número de DNI no puede ser null");
        }
        if (numeroDni.length() != LONGITUD_NUMERO_DNI) {
            throw new IllegalArgumentException("El número de DNI debe tener 8 dígitos");
        }
        for (int i = 0; i < numeroDni.length(); i++) {
            if (!Character.isDigit(numeroDni.charAt(i))) {
                throw new IllegalArgumentException("El número de DNI debe contener solo dígitos");
            }
        }

        int numero = Integer.parseInt(numeroDni); // admite ceros a la izquierda
        return LETRAS[numero % DIVISOR_DNI];
    }

    public boolean esValido(String dniCompleto) {
        if (dniCompleto == null) return false;
        if (dniCompleto.length() != LONGITUD_DNI_COMPLETO) return false;

        String numero = dniCompleto.substring(0, LONGITUD_NUMERO_DNI);
        char letra = Character.toUpperCase(dniCompleto.charAt(LONGITUD_NUMERO_DNI));

        // Número: 8 dígitos
        for (int i = 0; i < numero.length(); i++) {
            if (!Character.isDigit(numero.charAt(i))) return false;
        }

        // Letra final
        if (!Character.isLetter(letra)) return false;

        try {
            return calcularLetra(numero) == letra;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String generarAleatorioDNI() {
        int n = ThreadLocalRandom.current().nextInt(0, 100_000_000); // 0..99.999.999
        String numero = String.format("%08d", n);
        char letra = calcularLetra(numero);
        return numero + letra;
    }
}