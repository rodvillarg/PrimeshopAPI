package com.example.PrimeshopAPI.config;

import com.example.PrimeshopAPI.models.Categoria;
import com.example.PrimeshopAPI.models.Producto;
import com.example.PrimeshopAPI.models.RolUsuario;
import com.example.PrimeshopAPI.models.Usuario;
import com.example.PrimeshopAPI.repositories.CategoriaRepository;
import com.example.PrimeshopAPI.repositories.ProductoRepository;
import com.example.PrimeshopAPI.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Siembra datos de prueba la primera vez (un cliente demo y un catalogo
 * pequeno) para poder probar la API de inmediato.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByCorreo("cliente@primeshop.com").isEmpty()) {
            Usuario cliente = new Usuario("Cliente Demo", "cliente@primeshop.com",
                    passwordEncoder.encode("cliente123"), "6440000000", RolUsuario.CLIENTE);
            usuarioRepository.save(cliente);
        }

        if (categoriaRepository.count() == 0) {
            Categoria playera = categoriaRepository.save(new Categoria("Playera", "Playeras y camisetas"));
            Categoria tenis = categoriaRepository.save(new Categoria("Tenis", "Calzado deportivo"));
            Categoria sudadera = categoriaRepository.save(new Categoria("Sudadera", "Sudaderas y hoodies"));

            productoRepository.save(new Producto("Playera Basica", "Playera de algodon", 180, "", 30, playera));
            productoRepository.save(new Producto("Tenis Air", "Tenis deportivos", 500, "", 8, tenis));
            productoRepository.save(new Producto("Sudadera Oversize", "Sudadera holgada", 450, "", 15, sudadera));
            productoRepository.save(new Producto("Gorra Negra", "Gorra ajustable", 210, "", 0, playera));
        }
    }
}
