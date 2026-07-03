package com.example.PrimeshopAPI.excepcion;

/**
 * Se lanza cuando se pide una entidad que no existe. El manejador global
 * la traduce a HTTP 404.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public RecursoNoEncontradoException(String recurso, Object identificador) {
        super(recurso + " no encontrado con id: " + identificador);
    }
}
