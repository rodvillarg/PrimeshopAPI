package com.example.PrimeshopAPI.repositories;

import com.example.PrimeshopAPI.models.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByActivaTrue();
}
