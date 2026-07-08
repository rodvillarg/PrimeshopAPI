package com.example.PrimeshopAPI.dto;

/**
 * @author Jesús Rodrigo Villegas Argüelles - 261186
 */
public class PerfilDto {

    private int id;
    private String nombre;
    private String correo;
    private String telefono;
    private String rol;

    public PerfilDto() {
    }

    public PerfilDto(int id, String nombre, String correo, String telefono, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
