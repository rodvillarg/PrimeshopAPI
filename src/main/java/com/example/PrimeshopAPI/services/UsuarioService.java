package com.example.PrimeshopAPI.services;

import com.example.PrimeshopAPI.dto.RegistroDto;
import com.example.PrimeshopAPI.models.RolUsuario;
import com.example.PrimeshopAPI.models.Usuario;
import com.example.PrimeshopAPI.repositories.UsuarioRepository;
import java.util.Optional;
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
        usuario.setNombre(registroDto.getNombre());
        usuario.setCorreo(registroDto.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(registroDto.getContrasena()));
        usuario.setTelefono(registroDto.getTelefono());
        usuario.setRol(RolUsuario.CLIENTE);
        return usuarioRepository.save(usuario);
    }

    public boolean existeCorreo(String correo) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        return usuarioOpt.isPresent();
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public boolean contrasenaValida(String contrasenaPlano, String contrasenaHash) {
        return passwordEncoder.matches(contrasenaPlano, contrasenaHash);
    }
}
