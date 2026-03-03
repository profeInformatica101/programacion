package es.iescamas.market;

/**
 * Representa un producto del catálogo de la aplicación Market.
*/
public class Producto {


    /**
     * Construye un producto con todos sus datos.
     *
     * @param sku código único del producto (SKU)
     * @param nombre nombre comercial del producto
     * @param categoria categoría del producto (puede ser {@code null})
     * @param precio precio base del producto (sin descuentos aplicados)
     * @param rating valoración media del producto (p.ej., 0.0..5.0)
     * @param stock unidades disponibles en almacén
     * @param ventas unidades vendidas (para ordenar por “popularidad”)
     * @param porcentajeDescuento descuento en tanto por uno (0.0..1.0)
     * @param pesoKg peso en kilogramos (0.0 si no es enviable; para envío debe ser &gt; 0)
     */
    public Producto(
            String sku,
            String nombre,
            String categoria,
            double precio,
            double rating,
            int stock,
            int ventas,
            double porcentajeDescuento,
            double pesoKg
    ) {
        // TODO Auto-generated constructor stub
    }
}
