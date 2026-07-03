package com.example.PrimeshopAPI.dto;

import java.util.List;

/**
 * DTO de salida del carrito completo del usuario.
 */
public class CarritoDto {

    private List<ItemCarritoDto> items;
    private double total;
    private int cantidadItems;

    public CarritoDto() {
    }

    public CarritoDto(List<ItemCarritoDto> items, double total, int cantidadItems) {
        this.items = items;
        this.total = total;
        this.cantidadItems = cantidadItems;
    }

    public List<ItemCarritoDto> getItems() { return items; }
    public void setItems(List<ItemCarritoDto> items) { this.items = items; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public int getCantidadItems() { return cantidadItems; }
    public void setCantidadItems(int cantidadItems) { this.cantidadItems = cantidadItems; }
}
