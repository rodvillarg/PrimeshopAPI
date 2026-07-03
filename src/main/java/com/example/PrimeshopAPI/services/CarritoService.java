package com.example.PrimeshopAPI.services;

import com.example.PrimeshopAPI.dto.AgregarItemDto;
import com.example.PrimeshopAPI.dto.CarritoDto;
import com.example.PrimeshopAPI.dto.ItemCarritoDto;
import com.example.PrimeshopAPI.excepcion.RecursoNoEncontradoException;
import com.example.PrimeshopAPI.excepcion.ReglaNegocioException;
import com.example.PrimeshopAPI.models.Carrito;
import com.example.PrimeshopAPI.models.ItemCarrito;
import com.example.PrimeshopAPI.models.Producto;
import com.example.PrimeshopAPI.models.Usuario;
import com.example.PrimeshopAPI.repositories.CarritoRepository;
import com.example.PrimeshopAPI.repositories.ProductoRepository;
import com.example.PrimeshopAPI.repositories.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Logica del carrito de compras, siempre en el contexto del usuario autenticado
 * (identificado por su correo, que viene del token JWT).
 */
@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public CarritoService(CarritoRepository carritoRepository, ProductoRepository productoRepository,
            UsuarioRepository usuarioRepository) {
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public CarritoDto obtener(String correo) {
        return aDto(obtenerCarrito(correo));
    }

    @Transactional
    public CarritoDto agregar(String correo, AgregarItemDto dto) {
        Carrito carrito = obtenerCarrito(correo);
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto", dto.getProductoId()));
        if (!producto.isActivo()) {
            throw new ReglaNegocioException("El producto no esta disponible");
        }

        ItemCarrito item = buscarItem(carrito, producto.getId());
        int cantidadFinal = (item == null ? 0 : item.getCantidad()) + dto.getCantidad();
        if (cantidadFinal > producto.getStock()) {
            throw new ReglaNegocioException("Stock insuficiente. Disponible: " + producto.getStock());
        }

        if (item == null) {
            carrito.getItems().add(new ItemCarrito(carrito, producto, dto.getCantidad()));
        } else {
            item.setCantidad(cantidadFinal);
        }
        carritoRepository.save(carrito);
        return aDto(carrito);
    }

    @Transactional
    public CarritoDto actualizar(String correo, int productoId, int cantidad) {
        if (cantidad < 1) {
            throw new ReglaNegocioException("La cantidad debe ser al menos 1");
        }
        Carrito carrito = obtenerCarrito(correo);
        ItemCarrito item = buscarItem(carrito, productoId);
        if (item == null) {
            throw new RecursoNoEncontradoException("El producto no esta en el carrito");
        }
        if (cantidad > item.getProducto().getStock()) {
            throw new ReglaNegocioException("Stock insuficiente. Disponible: " + item.getProducto().getStock());
        }
        item.setCantidad(cantidad);
        carritoRepository.save(carrito);
        return aDto(carrito);
    }

    @Transactional
    public CarritoDto eliminar(String correo, int productoId) {
        Carrito carrito = obtenerCarrito(correo);
        boolean removido = carrito.getItems().removeIf(i -> i.getProducto().getId() == productoId);
        if (!removido) {
            throw new RecursoNoEncontradoException("El producto no esta en el carrito");
        }
        carritoRepository.save(carrito);
        return aDto(carrito);
    }

    @Transactional
    public void vaciar(String correo) {
        Carrito carrito = obtenerCarrito(correo);
        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Carrito obtenerCarrito(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario", correo));
        return carritoRepository.findByUsuarioId(usuario.getId())
                .orElseGet(() -> carritoRepository.save(new Carrito(usuario)));
    }

    private ItemCarrito buscarItem(Carrito carrito, int productoId) {
        return carrito.getItems().stream()
                .filter(i -> i.getProducto().getId() == productoId)
                .findFirst()
                .orElse(null);
    }

    private CarritoDto aDto(Carrito carrito) {
        List<ItemCarritoDto> items = carrito.getItems().stream()
                .map(i -> new ItemCarritoDto(i.getProducto().getId(), i.getProducto().getNombre(),
                        i.getProducto().getPrecio(), i.getCantidad(), i.getSubtotal()))
                .toList();
        int cantidadItems = carrito.getItems().stream().mapToInt(ItemCarrito::getCantidad).sum();
        return new CarritoDto(items, carrito.getTotal(), cantidadItems);
    }
}
