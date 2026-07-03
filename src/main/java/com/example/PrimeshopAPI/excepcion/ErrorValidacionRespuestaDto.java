package com.example.PrimeshopAPI.excepcion;

import java.util.Map;

/**
 * Extiende {@link ErrorRespuestaDto} agregando el detalle campo -> mensaje
 * cuando el error proviene de Bean Validation (@NotBlank, @Size, etc.).
 */
public class ErrorValidacionRespuestaDto extends ErrorRespuestaDto {

    private Map<String, String> errores;

    public ErrorValidacionRespuestaDto() {
    }

    public ErrorValidacionRespuestaDto(int status, String error, String message, String path,
            Map<String, String> errores) {
        super(status, error, message, path);
        this.errores = errores;
    }

    public Map<String, String> getErrores() { return errores; }
    public void setErrores(Map<String, String> errores) { this.errores = errores; }
}
