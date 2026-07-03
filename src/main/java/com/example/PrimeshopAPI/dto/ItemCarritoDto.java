package com.example.PrimeshopAPI.dto;

/**
 * DTO de salida de un renglon del carrito.
 */
public class ItemCarritoDto {

    private int productoId;
    private String nombre;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;

    public ItemCarritoDto() {
    }

    public ItemCarritoDto(int productoId, String nombre, double precioUnitario, int cantidad, double subtotal) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
