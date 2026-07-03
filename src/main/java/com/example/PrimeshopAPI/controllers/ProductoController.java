package com.example.PrimeshopAPI.controllers;

import com.example.PrimeshopAPI.dto.ProductoDto;
import com.example.PrimeshopAPI.services.ProductoService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * API publica del catalogo de productos (solo lectura).
 * Rutas: GET /api/productos (opcional ?categoriaId=), GET /api/productos/{id}
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoDto> listar(@RequestParam(required = false) Integer categoriaId) {
        return productoService.listar(categoriaId);
    }

    @GetMapping("/{id}")
    public ProductoDto obtener(@PathVariable int id) {
        return productoService.buscar(id);
    }
}
