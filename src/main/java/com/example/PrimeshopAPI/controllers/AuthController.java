package com.example.PrimeshopAPI.controllers;

import com.example.PrimeshopAPI.dto.AuthRespuestaDto;
import com.example.PrimeshopAPI.dto.LoginDto;
import com.example.PrimeshopAPI.dto.RegistroDto;
import com.example.PrimeshopAPI.models.Usuario;
import com.example.PrimeshopAPI.security.JwtService;
import com.example.PrimeshopAPI.services.UsuarioService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints públicos de autenticación: login y registro de clientes.
 * Son utilizados por las pantallas InicioSesion y Registro del storefront.
 *
 * @author Jesús Rodrigo Villegas Argüelles - 261186
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroDto registroDto) {
        if (usuarioService.existeCorreo(registroDto.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario con ese correo");
        }

        Usuario usuario = usuarioService.registrar(registroDto);
        String token = jwtService.generarToken(usuario.getCorreo());

        AuthRespuestaDto respuesta = new AuthRespuestaDto(token, usuario.getNombre(), usuario.getCorreo(), usuario.getRol().name());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorCorreo(loginDto.getCorreo());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuarioService.contrasenaValida(loginDto.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }

        String token = jwtService.generarToken(usuario.getCorreo());
        AuthRespuestaDto respuesta = new AuthRespuestaDto(token, usuario.getNombre(), usuario.getCorreo(), usuario.getRol().name());
        return ResponseEntity.ok(respuesta);
    }
}
