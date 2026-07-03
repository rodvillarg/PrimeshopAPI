package com.example.PrimeshopAPI.controllers;

import com.example.PrimeshopAPI.dto.PedidoDto;
import com.example.PrimeshopAPI.services.PedidoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API de pedidos del usuario autenticado (requiere JWT).
 * Rutas: POST /api/pedidos (checkout del carrito),
 *        GET /api/pedidos (historial), GET /api/pedidos/{id}
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoDto> crear(Authentication auth) {
        PedidoDto pedido = pedidoService.crearDesdeCarrito(auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @GetMapping
    public List<PedidoDto> listar(Authentication auth) {
        return pedidoService.listar(auth.getName());
    }

    @GetMapping("/{id}")
    public PedidoDto obtener(@PathVariable int id, Authentication auth) {
        return pedidoService.buscar(auth.getName(), id);
    }
}
