package com.zenware.catalogo.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class Producto {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @Min(value= 0, message = "El precio debe ser mayor o igual a cero")
    private Double precio;

    public Producto() {
    }

    public Producto(Long id, String nombre, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void aplicarImpuesto(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 50) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 50");
        }
        this.precio += this.precio * (porcentaje / 100.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto producto = (Producto) o;
        return Double.compare(producto.precio, precio) == 0 &&
                Objects.equals(id, producto.id) &&
                Objects.equals(nombre, producto.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, precio);
    }
}
