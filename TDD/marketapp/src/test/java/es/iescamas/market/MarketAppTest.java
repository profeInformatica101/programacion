package es.iescamas.market;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * MarketAppTest (TDD)
 * -------------------
 * Objetivo de la práctica (2 horas):
 *  - Aprender Interfaces (Descuentable, Enviable)
 *  - Practicar Comparable (orden natural) y Comparator (criterios alternativos)
 *  - Trabajar con ARRAYS (sin List/Streams)
 *  - Ordenación manual (burbuja) y funciones típicas de "negocio"
 *
 * Dinámica TDD obligatoria:
 *  1) Descomenta SOLO 1 test.
 *  2) Ejecuta tests: debe fallar (ROJO).
 *  3) Implementa lo mínimo para pasarlo (VERDE).
 *  4) Refactoriza sin romper (LIMPIO).
 *
 * Reglas:
 *  - Prohibido usar colecciones (List/ArrayList/Streams) y sort de librerías.
 *  - Los mensajes de excepción se comprueban: deben coincidir EXACTOS.
 *
 * Nota técnica:
 *  - Se evita el "delta" (1e-9) a propósito para no introducir tolerancias.
 *    Si algún test falla por decimales, revisa el cálculo o usa valores que den exacto.
 */
@DisplayName("MarketApp (TDD) - Interfaces + Comparable + Comparator (arrays)")
class MarketAppTest {

    private MarketApp app;

    @BeforeEach
    void setUp() {
        app = new MarketApp();
    }

    // =========================================================
    // DATASET BASE (tabla de productos)
    // =========================================================
    // Notas de diseño del dataset:
    // - Hay empates (ventas, precio, rating) para forzar desempates.
    // - Hay una categoría null para obligar a tratar null-safety.
    // - Hay productos con peso 0.0 (caso real: digital/no enviable).
    private Producto[] catalogoBase() {
        return new Producto[] {
            new Producto("LIC-120", "Antivirus EDU",      "Licencias", 19.99, 3.9, 100, 500, 0.10, 0.0),
            new Producto("LIB-014", "Effective Java",     "Libros",    45.50, 4.9,   8, 320, 0.00, 3.5),
            new Producto("LIB-001", "Clean Code",         "Libros",    34.95, 4.7,   3, 210, 0.05, 2.2),
            new Producto("LIB-002", "Refactoring",        "Libros",    41.00, 4.8,   1, 210, 0.00, 2.2), // empate ventas con LIB-001
            new Producto("HWD-011", "Ratón Vertical",     "Hardware",  29.90, 4.2,   2, 140, 0.00, 1.2),
            new Producto("HWD-500", "SSD 1TB",            "Hardware",  89.00, 4.6,   0,  95, 0.12, 4.0), // empate ventas con LIC-777
            new Producto("LIC-777", "IDE Pro (1 año)",    "Licencias", 89.00, 4.6,  50,  95, 0.00, 0.0), // empate precio+rating con HWD-500
            new Producto("ACC-100", "Mochila Portátil",   null,        24.99, 4.1,  20,  60, 0.20, 3.5)  // categoria null
        };
    }

    // =========================================================
    // ORDEN DE TRABAJO (descomentar en este orden)
    // =========================================================
    // 1) precioFinal (interface Descuentable)
    // 2) costeEnvio  (interface Enviable) + validación peso
    // 3) Comparable  (orden natural del Producto)
    // 4) ordenarNatural (burbuja + no modificar original)
    // 5) ordenar con Comparator (precio asc)
    // 6) ordenar con Comparator (categoría nullsLast + rating)
    // 7) filtrarPorCategoria (arrays)
    // 8) topN (arrays + comparator + validación n)
    // 9-16) tests extra de robustez y desempates
    //
    // 👉 Consejo para el alumnado:
    //    No descomentéis 10 tests de golpe. 1 test, 1 paso.
    //
    // ---------------------------------------------------------
    // A partir de aquí, TODO está comentado para TDD guiado.
    // ---------------------------------------------------------

    /*
    // =========================================
    // 1) INTERFAZ: Descuentable (problema real)
    // =========================================
    @Test
    @DisplayName("precioFinal: aplica descuento (interface Descuentable) correctamente")
    void precioFinal_aplicaDescuento() {
        Producto p = new Producto("X-1", "X", "Libros", 100.0, 4.0, 1, 1, 0.10, 0.0);

        double resEsperado = 90.0;
        double res = app.precioFinal(p, p.getPrecio());
        assertEquals(resEsperado, res, "Con 10% debe quedar 90€");

        Producto p2 = new Producto("X-2", "Y", "Libros", 80.0, 4.0, 1, 1, 0.00, 0.0);

        double resEsperado2 = 80.0;
        double res2 = app.precioFinal(p2, p2.getPrecio());
        assertEquals(resEsperado2, res2, "Con 0% el precio no cambia");
    }

    // ==================================================
    // 2) INTERFAZ: Enviable (coste de envío real)
    // ==================================================
    @Test
    @DisplayName("costeEnvio: calcula coste según peso (interface Enviable) y valida peso")
    void costeEnvio_basico_y_validacion() {
        Producto p = new Producto("X-3", "Algo", "Hardware", 10.0, 4.0, 1, 1, 0.0, 2.5);

        double resEsperado = 2.99 + (2.5 * 1.20);
        double res = app.costeEnvio(p);
        assertEquals(resEsperado, res, "Coste de envío incorrecto");

        Producto inval = new Producto("X-4", "Inval", "Hardware", 10.0, 4.0, 1, 1, 0.0, 0.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> app.costeEnvio(inval));
        assertEquals("peso invalido", ex.getMessage());
    }

    // =========================================
    // 3) Comparable: orden natural del Producto
    // =========================================
    @Test
    @DisplayName("Comparable: orden natural prioriza ventas desc, luego rating desc")
    void comparable_ventas_y_rating() {
        Producto a = new Producto("A", "Alpha", "Libros", 10, 4.9, 1, 100, 0, 1);
        Producto b = new Producto("B", "Beta",  "Libros", 10, 3.0, 1, 120, 0, 1);

        assertTrue(b.compareTo(a) < 0, "Más ventas debe ir primero en orden natural");
    }

    @Test
    @DisplayName("Comparable: si empatan ventas+rating, ordena por nombre A-Z, luego sku")
    void comparable_desempates_nombre_y_sku() {
        Producto p1 = new Producto("SKU-2", "Zorro", "Libros", 10, 4.5, 1, 50, 0, 1);
        Producto p2 = new Producto("SKU-1", "abeja", "Libros", 10, 4.5, 1, 50, 0, 1);

        assertTrue(p2.compareTo(p1) < 0, "Nombre A-Z (ignoreCase) decide");

        Producto p3 = new Producto("A-001", "Alpha", "Libros", 10, 4.5, 1, 50, 0, 1);
        Producto p4 = new Producto("B-001", "alpha", "Libros", 10, 4.5, 1, 50, 0, 1);

        assertTrue(p3.compareTo(p4) < 0, "Si nombre empata, sku asc estabiliza");
    }

    // =========================================
    // 4) Ordenación manual con tablas (burbuja)
    // =========================================
    @Test
    @DisplayName("ordenarNatural: devuelve copia ordenada y no modifica original")
    void ordenarNatural_noModificaOriginal() {
        Producto[] original = catalogoBase();
        Producto[] copiaAntes = app.copiar(original);

        Producto[] ordenado = app.ordenarNatural(original);

        assertArrayEquals(copiaAntes, original, "No debe modificar la tabla original");
        assertNotSame(original, ordenado, "Debe devolver una nueva tabla");

        String resEsperado = "LIC-120";
        assertEquals(resEsperado, ordenado[0].getSku(), "Más ventas primero (500)");
    }

    // =========================================
    // 5) Comparator: precio asc con desempate
    // =========================================
    @Test
    @DisplayName("Comparator precio asc: el más barato primero; empate por nombre")
    void ordenar_porPrecioAsc() {
        Producto[] t = catalogoBase();

        Producto[] res = app.ordenar(t, ComparadoresProducto.POR_PRECIO_ASC);

        String resEsperado = "LIC-120";
        assertEquals(resEsperado, res[0].getSku(), "19.99 debe ir primero");

        double resEsperadoPrecio = 19.99;
        assertEquals(resEsperadoPrecio, res[0].getPrecio(), "El precio del primero debe ser 19.99");
    }

    // =========================================
    // 6) Comparator: categoria asc (nulls last)
    // =========================================
    @Test
    @DisplayName("Comparator categoría nullsLast: categoria null debe quedar al final")
    void ordenar_porCategoria_nullsLast() {
        Producto[] t = catalogoBase();

        Producto[] res = app.ordenar(t, ComparadoresProducto.POR_CATEGORIA_Y_RATING);

        assertNull(res[res.length - 1].getCategoria(), "La categoría null debe quedar al final");
    }

    // =========================================
    // 7) Filtro por categoría (arrays)
    // =========================================
    @Test
    @DisplayName("filtrarPorCategoria: devuelve nueva tabla con coincidencias (incluye cat null)")
    void filtrarPorCategoria_basico() {
        Producto[] t = catalogoBase();

        Producto[] libros = app.filtrarPorCategoria(t, "Libros");

        int resEsperado = 3;
        assertEquals(resEsperado, libros.length, "En el dataset hay 3 libros");

        Producto[] nullCat = app.filtrarPorCategoria(t, null);

        int resEsperadoNull = 1;
        assertEquals(resEsperadoNull, nullCat.length, "Hay 1 producto con categoria null");

        String resEsperadoSku = "ACC-100";
        assertEquals(resEsperadoSku, nullCat[0].getSku());
    }

    // =========================================
    // 8) Top N (arrays + comparator)
    // =========================================
    @Test
    @DisplayName("topN: devuelve los N primeros según comparator y valida n")
    void topN_basico_y_validacion() {
        Producto[] t = catalogoBase();

        Producto[] top2 = app.topN(t, 2, ComparadoresProducto.POR_PRECIO_DESC);

        int resEsperado = 2;
        assertEquals(resEsperado, top2.length);

        assertTrue(top2[0].getPrecio() >= top2[1].getPrecio(), "Debe venir ordenado según el comparator");

        Producto[] topGrande = app.topN(t, 999, ComparadoresProducto.POR_PRECIO_ASC);

        int resEsperadoGrande = t.length;
        assertEquals(resEsperadoGrande, topGrande.length, "Si n es grande, devuelve todos");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> app.topN(t, 0, ComparadoresProducto.POR_PRECIO_ASC));
        assertEquals("n invalido", ex.getMessage());
    }

    // =========================================
    // 9) precioFinal: descuento extremo
    // =========================================
    @Test
    @DisplayName("precioFinal: con 100% descuento debe dar 0.0")
    void precioFinal_descuentoTotal() {
        Producto p = new Producto("D-100", "Gratis", "Libros", 50.0, 4.0, 1, 1, 1.0, 1.0);

        double resEsperado = 0.0;
        double res = app.precioFinal(p, p.getPrecio());
        assertEquals(resEsperado, res);
    }

    // =========================================
    // 10) costeEnvio: peso negativo inválido
    // =========================================
    @Test
    @DisplayName("costeEnvio: peso negativo lanza excepción (mensaje comprobado)")
    void costeEnvio_pesoNegativo() {
        Producto p = new Producto("P-NEG", "PesoNeg", "Hardware", 10.0, 4.0, 1, 1, 0.0, -0.5);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> app.costeEnvio(p));
        assertEquals("peso invalido", ex.getMessage());
    }

    // =========================================
    // 11) ordenarNatural: empate ventas -> rating desc decide
    // =========================================
    @Test
    @DisplayName("ordenarNatural: si ventas empatan, rating desc decide")
    void ordenarNatural_empateVentas_decideRating() {
        Producto[] t = new Producto[] {
            new Producto("A", "A", "Libros", 10, 4.2, 1, 100, 0, 1),
            new Producto("B", "B", "Libros", 10, 4.9, 1, 100, 0, 1)
        };

        Producto[] res = app.ordenarNatural(t);

        String resEsperado = "B";
        assertEquals(resEsperado, res[0].getSku(), "Con mismas ventas, mayor rating debe ir primero");
    }

    // =========================================
    // 12) ordenarNatural: empate ventas+rating -> nombre A-Z ignoreCase
    // =========================================
    @Test
    @DisplayName("ordenarNatural: empate ventas+rating -> nombre A-Z (ignoreCase)")
    void ordenarNatural_empateVentasRating_decideNombre() {
        Producto[] t = new Producto[] {
            new Producto("SKU-1", "Zorro", "Libros", 10, 4.5, 1, 50, 0, 1),
            new Producto("SKU-2", "abeja", "Libros", 10, 4.5, 1, 50, 0, 1)
        };

        Producto[] res = app.ordenarNatural(t);

        String resEsperado = "SKU-2";
        assertEquals(resEsperado, res[0].getSku(), "abeja debe ir antes que Zorro (ignoreCase)");
    }

    // =========================================
    // 13) ordenarNatural: empate total -> sku asc ignoreCase
    // =========================================
    @Test
    @DisplayName("ordenarNatural: empate total -> sku asc (ignoreCase) estabiliza")
    void ordenarNatural_empateTotal_decideSku() {
        Producto[] t = new Producto[] {
            new Producto("B-001", "Alpha", "Libros", 10, 4.5, 1, 50, 0, 1),
            new Producto("a-001", "alpha", "Libros", 10, 4.5, 1, 50, 0, 1)
        };

        Producto[] res = app.ordenarNatural(t);

        String resEsperado = "a-001";
        assertEquals(resEsperado, res[0].getSku(), "SKU a-001 debe ir antes que B-001 (ignoreCase)");
    }

    // =========================================
    // 14) ordenar (Comparator precio desc): empate por nombre asc
    // =========================================
    @Test
    @DisplayName("ordenar (Comparator precio desc): empate de precio se resuelve por nombre A-Z")
    void ordenar_precioDesc_empateNombre() {
        Producto[] t = new Producto[] {
            new Producto("X1", "Beta",  "Hardware", 10.0, 4.0, 1, 1, 0, 1),
            new Producto("X2", "Alpha", "Hardware", 10.0, 4.0, 1, 1, 0, 1)
        };

        Producto[] res = app.ordenar(t, ComparadoresProducto.POR_PRECIO_DESC);

        String resEsperado = "X2";
        assertEquals(resEsperado, res[0].getSku(), "Con mismo precio, Alpha debe ir antes que Beta");
    }

    // =========================================
    // 15) categoría nullsLast: null siempre al final
    // =========================================
    @Test
    @DisplayName("Comparator categoría nullsLast: null siempre al final, aunque su rating sea alto")
    void ordenar_categoriaNullsLast_siemprefinal() {
        Producto[] t = new Producto[] {
            new Producto("N-1", "NullTop", null, 10.0, 5.0, 1, 1, 0, 1),
            new Producto("H-1", "HardwareMed", "Hardware", 10.0, 1.0, 1, 1, 0, 1),
            new Producto("L-1", "LibrosMed",   "Libros",   10.0, 1.0, 1, 1, 0, 1)
        };

        Producto[] res = app.ordenar(t, ComparadoresProducto.POR_CATEGORIA_Y_RATING);

        assertNull(res[res.length - 1].getCategoria(), "El null debe quedar al final aunque tenga rating 5.0");

        String resEsperado = "N-1";
        assertEquals(resEsperado, res[res.length - 1].getSku());
    }

    // =========================================
    // 16) topN: no modifica original y respeta orden
    // =========================================
    @Test
    @DisplayName("topN: devuelve N según comparator y no modifica el array original")
    void topN_noModificaOriginal_y_ordenCorrecto() {
        Producto[] original = catalogoBase();
        Producto[] copiaAntes = app.copiar(original);

        Producto[] top3 = app.topN(original, 3, ComparadoresProducto.POR_PRECIO_ASC);

        assertArrayEquals(copiaAntes, original, "topN no debe modificar la tabla original");

        int resEsperado = 3;
        assertEquals(resEsperado, top3.length);

        assertTrue(top3[0].getPrecio() <= top3[1].getPrecio());
        assertTrue(top3[1].getPrecio() <= top3[2].getPrecio());
    }
    */
}