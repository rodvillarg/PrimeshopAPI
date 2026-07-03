package com.example.PrimeshopAPI.repositories;

import com.example.PrimeshopAPI.models.Carrito;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    Optional<Carrito> findByUsuarioId(int usuarioId);
}
