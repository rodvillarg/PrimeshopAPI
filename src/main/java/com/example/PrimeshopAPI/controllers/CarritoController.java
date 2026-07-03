package com.example.PrimeshopAPI.controllers;

import com.example.PrimeshopAPI.dto.ActualizarCantidadDto;
import com.example.PrimeshopAPI.dto.AgregarItemDto;
import com.example.PrimeshopAPI.dto.CarritoDto;
import com.example.PrimeshopAPI.services.CarritoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API del carrito del usuario autenticado (requiere JWT).
 * El usuario se identifica por su correo, tomado del token (Authentication).
 * Rutas: GET /api/carrito, POST /api/carrito/items,
 *        PUT /api/carrito/items/{productoId}, DELETE /api/carrito/items/{productoId},
 *        DELETE /api/carrito
 */
@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
    public CarritoDto obtener(Authentication auth) {
        return carritoService.obtener(auth.getName());
    }

    @PostMapping("/items")
    public ResponseEntity<CarritoDto> agregar(@Valid @RequestBody AgregarItemDto dto, Authentication auth) {
        CarritoDto carrito = carritoService.agregar(auth.getName(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrito);
    }

    @PutMapping("/items/{productoId}")
    public CarritoDto actualizar(@PathVariable int productoId, @Valid @RequestBody ActualizarCantidadDto dto,
            Authentication auth) {
        return carritoService.actualizar(auth.getName(), productoId, dto.getCantidad());
    }

    @DeleteMapping("/items/{productoId}")
    public CarritoDto eliminar(@PathVariable int productoId, Authentication auth) {
        return carritoService.eliminar(auth.getName(), productoId);
    }

    @DeleteMapping
    public ResponseEntity<Void> vaciar(Authentication auth) {
        carritoService.vaciar(auth.getName());
        return ResponseEntity.noContent().build();
    }
}
