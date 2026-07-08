package com.example.PrimeshopAPI.services;

import com.example.PrimeshopAPI.dto.RegistroDto;
import com.example.PrimeshopAPI.dto.ActualizarPerfilDto;
import com.example.PrimeshopAPI.models.RolUsuario;
import com.example.PrimeshopAPI.models.Usuario;
import com.example.PrimeshopAPI.repositories.UsuarioRepository;
import java.util.Optional;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Jesús Rodrigo Villegas Argüelles - 261186
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrar(RegistroDto registroDto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDto.getNombre().trim());
        usuario.setCorreo(registroDto.getCorreo().trim().toLowerCase());
        usuario.setContrasena(passwordEncoder.encode(registroDto.getContrasena()));
        usuario.setTelefono(registroDto.getTelefono());
        usuario.setRol(RolUsuario.CLIENTE);
        return usuarioRepository.save(usuario);
    }

    public boolean existeCorreo(String correo) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo.trim().toLowerCase());
        return usuarioOpt.isPresent();
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo.trim().toLowerCase());
    }

    public boolean contrasenaValida(String contrasenaPlano, String contrasenaHash) {
        return passwordEncoder.matches(contrasenaPlano, contrasenaHash);
    }

    public Usuario actualizarPerfil(String correo, ActualizarPerfilDto dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isEmpty()) {
            throw new NoSuchElementException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setNombre(dto.getNombre().trim());
        usuario.setTelefono(dto.getTelefono());

        boolean quiereCambiarContrasena = dto.getContrasenaNueva() != null && !dto.getContrasenaNueva().isBlank();

        if (quiereCambiarContrasena) {
            if (dto.getContrasenaNueva().length() < 6 || dto.getContrasenaNueva().length() > 50) {
                throw new IllegalArgumentException("La nueva contraseña debe tener al menos 6 caracteres");
            }

            boolean actualCorrecta = contrasenaValida(dto.getContrasenaActual(), usuario.getContrasena());

            if (!actualCorrecta) {
                throw new IllegalArgumentException("La contraseña actual no es correcta");
            }

            usuario.setContrasena(passwordEncoder.encode(dto.getContrasenaNueva()));
        }

        return usuarioRepository.save(usuario);
    }
}
