package com.example.PrimeshopAPI.dto;

/**
 * DTO de salida de un producto del catalogo.
 */
public class ProductoDto {

    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String imagenUrl;
    private int stock;
    private boolean disponible;
    private int categoriaId;
    private String categoriaNombre;

    public ProductoDto() {
    }

    public ProductoDto(int id, String nombre, String descripcion, double precio, String imagenUrl,
            int stock, boolean disponible, int categoriaId, String categoriaNombre) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.stock = stock;
        this.disponible = disponible;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }

    public String getCategoriaNombre() { return categoriaNombre; }
    public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }
}
