package com.example.PrimeshopAPI.excepcion;

import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Manejo centralizado de excepciones de la API REST. Toda respuesta de error
 * es un {@link ErrorRespuestaDto} en JSON; el stacktrace solo va al log.
 */
@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    private static final Logger log = LoggerFactory.getLogger(ManejadorGlobalExcepciones.class);

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorRespuestaDto> noEncontrado(RecursoNoEncontradoException ex, HttpServletRequest req) {
        return construir(HttpStatus.NOT_FOUND, "Recurso no encontrado", ex.getMessage(), req);
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ErrorRespuestaDto> reglaNegocio(ReglaNegocioException ex, HttpServletRequest req) {
        return construir(HttpStatus.UNPROCESSABLE_ENTITY, "Regla de negocio violada", ex.getMessage(), req);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorRespuestaDto> accesoDenegado(AccessDeniedException ex, HttpServletRequest req) {
        return construir(HttpStatus.FORBIDDEN, "Acceso denegado",
                "No tienes permisos para realizar esta accion", req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorValidacionRespuestaDto> validacion(MethodArgumentNotValidException ex,
            HttpServletRequest req) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errores.put(fe.getField(), fe.getDefaultMessage());
        }
        ErrorValidacionRespuestaDto body = new ErrorValidacionRespuestaDto(
                HttpStatus.BAD_REQUEST.value(), "Error de validacion",
                "Uno o mas campos no son validos", req.getRequestURI(), errores);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRespuestaDto> jsonMalformado(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return construir(HttpStatus.BAD_REQUEST, "Solicitud mal formada",
                "El cuerpo de la solicitud no es un JSON valido", req);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorRespuestaDto> tipoInvalido(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        return construir(HttpStatus.BAD_REQUEST, "Parametro invalido",
                "El parametro '" + ex.getName() + "' tiene un formato invalido", req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRespuestaDto> error(Exception ex, HttpServletRequest req) {
        log.error("Error no controlado en {} {}", req.getMethod(), req.getRequestURI(), ex);
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor",
                "Ocurrio un error inesperado. Intenta de nuevo mas tarde.", req);
    }

    private ResponseEntity<ErrorRespuestaDto> construir(HttpStatus status, String error, String mensaje,
            HttpServletRequest req) {
        ErrorRespuestaDto body = new ErrorRespuestaDto(status.value(), error, mensaje, req.getRequestURI());
        return ResponseEntity.status(status).body(body);
    }
}
