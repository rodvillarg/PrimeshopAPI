package com.example.PrimeshopAPI.excepcion;

import java.time.LocalDateTime;

/**
 * Estructura JSON unica y consistente para todos los errores de la API.
 * Nunca incluye el stacktrace: solo informacion segura para el cliente.
 */
public class ErrorRespuestaDto {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorRespuestaDto() {
    }

    public ErrorRespuestaDto(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() { return timestamp; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
