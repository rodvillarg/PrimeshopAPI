package com.example.PrimeshopAPI.security;

import com.example.PrimeshopAPI.models.Usuario;
import com.example.PrimeshopAPI.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Jesús Rodrigo Villegas Argüelles - 261186
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtService.esTokenValido(token)) {
                String correo = jwtService.extraerCorreo(token);
                Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

                if (usuarioOpt.isPresent()) {
                    Usuario usuario = usuarioOpt.get();

                    List<GrantedAuthority> autoridades = new ArrayList<GrantedAuthority>();
                    autoridades.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(usuario.getCorreo(), null, autoridades);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }
}