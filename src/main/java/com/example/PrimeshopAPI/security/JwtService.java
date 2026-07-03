package com.example.PrimeshopAPI.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de generar y validar los tokens JWT.
 *
 * @author Jesús Rodrigo Villegas Argüelles - 261186
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiracion;

    private SecretKey obtenerLlave() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generarToken(String correo) {
        Date ahora = new Date();
        Date fechaExpiracion = new Date(ahora.getTime() + expiracion);

        return Jwts.builder()
                .subject(correo)
                .issuedAt(ahora)
                .expiration(fechaExpiracion)
                .signWith(obtenerLlave())
                .compact();
    }

    public String extraerCorreo(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(obtenerLlave())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean esTokenValido(String token) {
        try {
            Jwts.parser()
                    .verifyWith(obtenerLlave())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
