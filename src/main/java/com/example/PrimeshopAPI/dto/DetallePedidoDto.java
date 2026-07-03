package com.example.PrimeshopAPI.dto;

/**
 * DTO de salida de una linea de un pedido.
 */
public class DetallePedidoDto {

    private int productoId;
    private String nombre;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetallePedidoDto() {
    }

    public DetallePedidoDto(int productoId, String nombre, int cantidad, double precioUnitario, double subtotal) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
