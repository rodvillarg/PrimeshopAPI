package com.example.PrimeshopAPI.excepcion;

/**
 * Se lanza cuando una operacion es valida sintacticamente pero rompe una
 * regla de negocio (ej. stock insuficiente, carrito vacio). El manejador
 * global la traduce a HTTP 422.
 */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
