package com.zenware.catalogo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    @Test
    public void aplicarImpuesto_valido_aumentaPrecio() {
        Producto producto = new Producto(1L, "Taza de chocorramo", 100.0);
        producto.aplicarImpuesto(10.0);
        assertEquals(110.0, producto.getPrecio(), 0.0001);
    }

    @Test
    public void aplicarImpuesto_fueraDeRango_lanzaExcepcion() {
        Producto producto = new Producto(1L, "Taza de Colcafé", 100.0);
        assertThrows(IllegalArgumentException.class, () -> producto.aplicarImpuesto(60.0));
        assertThrows(IllegalArgumentException.class, () -> producto.aplicarImpuesto(-5.0));
    }
}
