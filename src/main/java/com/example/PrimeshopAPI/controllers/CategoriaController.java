package com.example.PrimeshopAPI.controllers;

import com.example.PrimeshopAPI.dto.CategoriaDto;
import com.example.PrimeshopAPI.services.CategoriaService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API publica del catalogo de categorias (solo lectura).
 * Rutas: GET /api/categorias, GET /api/categorias/{id}
 */
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaDto> listar() {
        return categoriaService.listar();
    }

    @GetMapping("/{id}")
    public CategoriaDto obtener(@PathVariable int id) {
        return categoriaService.buscar(id);
    }
}
