package com.example.PrimeshopAPI.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de salida de un pedido con sus lineas.
 */
public class PedidoDto {

    private int id;
    private String numeroPedido;
    private LocalDateTime fecha;
    private double total;
    private String estado;
    private List<DetallePedidoDto> detalles;

    public PedidoDto() {
    }

    public PedidoDto(int id, String numeroPedido, LocalDateTime fecha, double total, String estado,
            List<DetallePedidoDto> detalles) {
        this.id = id;
        this.numeroPedido = numeroPedido;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.detalles = detalles;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<DetallePedidoDto> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoDto> detalles) { this.detalles = detalles; }
}
