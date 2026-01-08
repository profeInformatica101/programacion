package es.dni;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
@DisplayName("ControladorDNITest")
class ControladorDNITest {

    private static ControladorDNI controladorDNI;

    @BeforeAll
    static void beforeAll() {
        controladorDNI = new ControladorDNI();
    }

    @BeforeEach
    void beforeEach() {
        // SUT sin estado: no requiere reset, pero mantenemos el patrón.
    }

    @AfterEach
    void afterEach() {
        // Limpieza por test si se añadieran recursos en el futuro.
    }

    @AfterAll
    static void afterAll() {
        controladorDNI = null;
    }

    @Test
    @DisplayName("testCalcularLetraValido()")
    void testCalcularLetraValido() {
        // Arrange
        String numeroDni = "12345678";
        char letraEsperada = 'Z';

        // Act
        char letraCalculada = controladorDNI.calcularLetra(numeroDni);

        // Assert
        assertEquals(letraEsperada, letraCalculada);
    }

    @Test
    @DisplayName("testCalcularLetraRangosExtremos()")
    void testCalcularLetraRangosExtremos() {
        // Arrange
        String numeroMin = "00000001";
        char letraEsperadaMin = 'R';

        String numeroMax = "99999999";
        char letraEsperadaMax = 'R';

        // Act
        char letraCalculadaMin = controladorDNI.calcularLetra(numeroMin);
        char letraCalculadaMax = controladorDNI.calcularLetra(numeroMax);

        // Assert
        assertAll(
                () -> assertEquals(letraEsperadaMin, letraCalculadaMin),
                () -> assertEquals(letraEsperadaMax, letraCalculadaMax)
        );
    }

    @Test
    @DisplayName("testEsValido_DniValido()")
    void testEsValido_DniValido() {
        // Arrange
        String dniValido = "12345678Z";
        String dniMinimoValido = "00000001R";
        String dniMaximoValido = "99999999R";

        // Act
        boolean r1 = controladorDNI.esValido(dniValido);
        boolean r2 = controladorDNI.esValido(dniMinimoValido);
        boolean r3 = controladorDNI.esValido(dniMaximoValido);

        // Assert
        assertAll(
                () -> assertTrue(r1),
                () -> assertTrue(r2),
                () -> assertTrue(r3)
        );
    }

    @Test
    @DisplayName("testEsValido_DniInvalido()")
    void testEsValido_DniInvalido() {
        // Arrange
        String dniLetraIncorrecta = "12345678A";

        // Act
        boolean resultado = controladorDNI.esValido(dniLetraIncorrecta);

        // Assert
        assertFalse(resultado);
    }

    @Test
    @DisplayName("testEsValido_DniEntradasInvalidas()")
    void testEsValido_DniEntradasInvalidas() {
        // Arrange (casos de la imagen)
        String menosDe8Digitos = "1234567Z";
        String masDe8Digitos = "123456789Z";
        String caracteresNoNumericos = "abcdefghi";
        String entradaNula = null;

        // Act
        boolean r1 = controladorDNI.esValido(menosDe8Digitos);
        boolean r2 = controladorDNI.esValido(masDe8Digitos);
        boolean r3 = controladorDNI.esValido(caracteresNoNumericos);
        boolean r4 = controladorDNI.esValido(entradaNula);

        // Assert
        assertAll(
                () -> assertFalse(r1),
                () -> assertFalse(r2),
                () -> assertFalse(r3),
                () -> assertFalse(r4)
        );
    }

    @Test
    @DisplayName("testGenerarAleatorioDNI()")
    void testGenerarAleatorioDNI() {
        // Act
        String dniGenerado = controladorDNI.generarAleatorioDNI();

        // Assert
        assertAll(
                () -> assertNotNull(dniGenerado),
                () -> assertEquals(ControladorDNI.LONGITUD_DNI_COMPLETO, dniGenerado.length()),
                () -> assertTrue(controladorDNI.esValido(dniGenerado))
        );
    }

    // =========================================================
    // Comprobación de EXCEPCIONES (sin añadir nuevos tests "extra")
    // Lo integramos de forma profesional dentro del test más afín:
    // testCalcularLetraRangosExtremos()
    // =========================================================

    @Test
    @DisplayName("testCalcularLetraRangosExtremos() - incluye comprobación de excepciones")
    void testCalcularLetraRangosExtremos_incluyeExcepciones() {
        // --- Parte A: extremos (mantiene el espíritu del test del diagrama) ---
        String numeroMin = "00000001";
        char letraEsperadaMin = 'R';

        String numeroMax = "99999999";
        char letraEsperadaMax = 'R';

        char letraCalculadaMin = controladorDNI.calcularLetra(numeroMin);
        char letraCalculadaMax = controladorDNI.calcularLetra(numeroMax);

        assertAll(
                () -> assertEquals(letraEsperadaMin, letraCalculadaMin),
                () -> assertEquals(letraEsperadaMax, letraCalculadaMax)
        );

        // --- Parte B: excepciones que debe lanzar calcularLetra(...) ---
        IllegalArgumentException exNull = assertThrows(
                IllegalArgumentException.class,
                () -> controladorDNI.calcularLetra(null)
        );
        assertEquals("El número de DNI no puede ser null", exNull.getMessage());

        IllegalArgumentException exLongitud = assertThrows(
                IllegalArgumentException.class,
                () -> controladorDNI.calcularLetra("1234567") // 7 dígitos
        );
        assertEquals("El número de DNI debe tener 8 dígitos", exLongitud.getMessage());

        IllegalArgumentException exNoDigitos = assertThrows(
                IllegalArgumentException.class,
                () -> controladorDNI.calcularLetra("1234A678")
        );
        assertEquals("El número de DNI debe contener solo dígitos", exNoDigitos.getMessage());
    }
    
    // =========================================================
    // BLOQUE AMPLIADO “PROFESIONAL” (para usar como plantilla)
    // =========================================================

    @Nested
    @DisplayName("Contrato de excepciones de calcularLetra(...)")
    class CalcularLetraExcepciones {

        @Test
        @DisplayName("testCalcularLetra_Null_LanzaIllegalArgumentException()")
        void testCalcularLetra_Null_LanzaIllegalArgumentException() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> controladorDNI.calcularLetra(null)
            );
            assertEquals("El número de DNI no puede ser null", ex.getMessage());
        }

        @ParameterizedTest(name = "Longitud inválida: \"{0}\"")
        @ValueSource(strings = { "", "1", "1234567", "123456789", "1234567890" })
        @DisplayName("testCalcularLetra_LongitudIncorrecta_LanzaIllegalArgumentException()")
        void testCalcularLetra_LongitudIncorrecta_LanzaIllegalArgumentException(String entrada) {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> controladorDNI.calcularLetra(entrada)
            );
            assertEquals("El número de DNI debe tener 8 dígitos", ex.getMessage());
        }

        @ParameterizedTest(name = "No numérico: \"{0}\"")
        @ValueSource(strings = { "1234A678", "abcd5678", "12-45678", "1234567 " })
        @DisplayName("testCalcularLetra_NoNumerico_LanzaIllegalArgumentException()")
        void testCalcularLetra_NoNumerico_LanzaIllegalArgumentException(String entrada) {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> controladorDNI.calcularLetra(entrada)
            );
            assertEquals("El número de DNI debe contener solo dígitos", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Robustez adicional de esValido(...)")
    class EsValidoRobustez {

        @Test
        @DisplayName("testEsValido_AceptaLetraMinuscula()")
        void testEsValido_AceptaLetraMinuscula() {
            assertTrue(controladorDNI.esValido("12345678z"));
        }

        @ParameterizedTest(name = "Entrada inválida: \"{0}\" -> false")
        @ValueSource(strings = {
                "",                 // vacío
                "         ",         // blancos
                "12345678",          // falta letra
                "1234567Z",          // 7 dígitos + letra
                "123456789Z",        // 9 dígitos + letra
                "12345678#",         // letra final no válida
                "1234A678Z",         // letra dentro del número
                "0000000AR",         // no numérico en número
                "ABCDEFGHZ"          // no numérico en número
        })
        @DisplayName("testEsValido_EntradasInvalidas_Parametrizado()")
        void testEsValido_EntradasInvalidas_Parametrizado(String entrada) {
            assertFalse(controladorDNI.esValido(entrada));
        }
    }

    @Nested
    @DisplayName("Propiedades del sistema")
    class Propiedades {

        private final Set<Character> letrasPermitidas = Set.of(
                'T','R','W','A','G','M','Y','F','P','D','X','B',
                'N','J','Z','S','Q','V','H','L','C','K','E'
        );

        @RepeatedTest(200)
        @DisplayName("testCalcularLetra_SiempreDevuelveLetraDelConjunto()")
        void testCalcularLetra_SiempreDevuelveLetraDelConjunto() {
            // Genera números aleatorios en formato de 8 dígitos (incluye ceros a la izquierda)
            int n = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 100_000_000);
            String numero = String.format("%08d", n);

            char letra = controladorDNI.calcularLetra(numero);
            assertTrue(letrasPermitidas.contains(letra));
        }

        @RepeatedTest(200)
        @DisplayName("testGenerarAleatorioDNI_SiempreValido_Repetido()")
        void testGenerarAleatorioDNI_SiempreValido_Repetido() {
            String dni = controladorDNI.generarAleatorioDNI();

            assertAll(
                    () -> assertNotNull(dni),
                    () -> assertEquals(ControladorDNI.LONGITUD_DNI_COMPLETO, dni.length()),
                    () -> assertTrue(controladorDNI.esValido(dni))
            );
        }
    }
}