package com.example.PrimeshopAPI.services;

import com.example.PrimeshopAPI.dto.ProductoDto;
import com.example.PrimeshopAPI.excepcion.RecursoNoEncontradoException;
import com.example.PrimeshopAPI.models.Producto;
import com.example.PrimeshopAPI.repositories.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Logica de negocio del catalogo de productos (solo lectura para el storefront).
 */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoDto> listar(Integer categoriaId) {
        List<Producto> productos = (categoriaId == null)
                ? productoRepository.findByActivoTrue()
                : productoRepository.findByActivoTrueAndCategoriaId(categoriaId);
        return productos.stream().map(this::aDto).toList();
    }

    @Transactional(readOnly = true)
    public ProductoDto buscar(int id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto", id));
        return aDto(producto);
    }

    private ProductoDto aDto(Producto p) {
        return new ProductoDto(
                p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getImagenUrl(),
                p.getStock(), p.getStock() > 0,
                p.getCategoria().getId(), p.getCategoria().getNombre());
    }
}
