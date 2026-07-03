package com.example.PrimeshopAPI.repositories;

import com.example.PrimeshopAPI.models.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByActivoTrue();

    List<Producto> findByActivoTrueAndCategoriaId(int categoriaId);
}
