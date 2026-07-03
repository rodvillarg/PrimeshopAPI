package com.example.PrimeshopAPI.services;

import com.example.PrimeshopAPI.dto.DetallePedidoDto;
import com.example.PrimeshopAPI.dto.PedidoDto;
import com.example.PrimeshopAPI.excepcion.RecursoNoEncontradoException;
import com.example.PrimeshopAPI.excepcion.ReglaNegocioException;
import com.example.PrimeshopAPI.models.Carrito;
import com.example.PrimeshopAPI.models.DetallePedido;
import com.example.PrimeshopAPI.models.ItemCarrito;
import com.example.PrimeshopAPI.models.Pedido;
import com.example.PrimeshopAPI.models.Producto;
import com.example.PrimeshopAPI.models.Usuario;
import com.example.PrimeshopAPI.repositories.CarritoRepository;
import com.example.PrimeshopAPI.repositories.PedidoRepository;
import com.example.PrimeshopAPI.repositories.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Logica de pedidos: checkout (crea el pedido a partir del carrito, descuenta
 * stock y vacia el carrito) y consulta del historial del usuario.
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository, CarritoRepository carritoRepository,
            UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.carritoRepository = carritoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public PedidoDto crearDesdeCarrito(String correo) {
        Usuario usuario = usuario(correo);
        Carrito carrito = carritoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ReglaNegocioException("Tu carrito esta vacio"));
        if (carrito.getItems().isEmpty()) {
            throw new ReglaNegocioException("Tu carrito esta vacio");
        }

        Pedido pedido = new Pedido(generarNumero(), usuario);
        double total = 0;
        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = item.getProducto();
            if (item.getCantidad() > producto.getStock()) {
                throw new ReglaNegocioException("Stock insuficiente para '" + producto.getNombre()
                        + "'. Disponible: " + producto.getStock());
            }
            DetallePedido detalle = new DetallePedido(producto, item.getCantidad(), producto.getPrecio());
            pedido.agregarDetalle(detalle);
            total += detalle.getSubtotal();
            producto.setStock(producto.getStock() - item.getCantidad());
        }
        pedido.setTotal(total);
        Pedido guardado = pedidoRepository.save(pedido);

        carrito.getItems().clear();
        carritoRepository.save(carrito);

        return aDto(guardado);
    }

    @Transactional(readOnly = true)
    public List<PedidoDto> listar(String correo) {
        Usuario usuario = usuario(correo);
        return pedidoRepository.findByUsuarioIdOrderByFechaDesc(usuario.getId()).stream()
                .map(this::aDto).toList();
    }

    @Transactional(readOnly = true)
    public PedidoDto buscar(String correo, int id) {
        Usuario usuario = usuario(correo);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", id));
        if (pedido.getUsuario().getId() != usuario.getId()) {
            // No revelar que el pedido existe pero es de otro usuario.
            throw new RecursoNoEncontradoException("Pedido", id);
        }
        return aDto(pedido);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Usuario usuario(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario", correo));
    }

    private String generarNumero() {
        return "PS-" + System.currentTimeMillis();
    }

    private PedidoDto aDto(Pedido p) {
        List<DetallePedidoDto> detalles = p.getDetalles().stream()
                .map(d -> new DetallePedidoDto(d.getProducto().getId(), d.getProducto().getNombre(),
                        d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal()))
                .toList();
        return new PedidoDto(p.getId(), p.getNumeroPedido(), p.getFecha(), p.getTotal(),
                p.getEstado().name(), detalles);
    }
}
