package com.example.PrimeshopAPI.controllers;

import com.example.PrimeshopAPI.dto.ActualizarPerfilDto;
import com.example.PrimeshopAPI.dto.PerfilDto;
import com.example.PrimeshopAPI.models.Usuario;
import com.example.PrimeshopAPI.services.UsuarioService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesús Rodrigo Villegas Argüelles - 261186
 */
@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> obtenerPerfil(Principal principal) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorCorreo(principal.getName());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        PerfilDto perfil = new PerfilDto(usuario.getId(), usuario.getNombre(), usuario.getCorreo(),
                usuario.getTelefono(), usuario.getRol().name());
        return ResponseEntity.ok(perfil);
    }

    @PutMapping
    public ResponseEntity<?> actualizarPerfil(@Valid @RequestBody ActualizarPerfilDto dto, Principal principal) {
        try {
            Usuario actualizado = usuarioService.actualizarPerfil(principal.getName(), dto);
            PerfilDto perfil = new PerfilDto(actualizado.getId(), actualizado.getNombre(), actualizado.getCorreo(),
                    actualizado.getTelefono(), actualizado.getRol().name());
            return ResponseEntity.ok(perfil);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}