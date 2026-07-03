package com.example.PrimeshopAPI.services;

import com.example.PrimeshopAPI.dto.CategoriaDto;
import com.example.PrimeshopAPI.excepcion.RecursoNoEncontradoException;
import com.example.PrimeshopAPI.models.Categoria;
import com.example.PrimeshopAPI.repositories.CategoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Logica de negocio del catalogo de categorias (solo lectura para el storefront).
 */
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaDto> listar() {
        return categoriaRepository.findByActivaTrue().stream().map(this::aDto).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaDto buscar(int id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria", id));
        return aDto(categoria);
    }

    private CategoriaDto aDto(Categoria c) {
        return new CategoriaDto(c.getId(), c.getNombre(), c.getDescripcion());
    }
}
