package com.example.PrimeshopAPI.repositories;

import com.example.PrimeshopAPI.models.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByUsuarioIdOrderByFechaDesc(int usuarioId);
}
