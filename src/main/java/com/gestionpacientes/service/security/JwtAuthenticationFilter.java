package com.gestionpacientes.service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Buscamos la cabecera "Authorization"
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Si no hay cabecera o no empieza con Bearer, seguimos al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 2. Extraemos el token y el correo del usuario
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);

            // 3. Si tenemos email y el usuario no está ya autenticado en el sistema...
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Buscamos al usuario en la BD
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // 4. Validamos si el token sigue siendo legal
                if (jwtService.isTokenValid(jwt, userDetails)) {

                    // Creamos el "pase" de seguridad (Authentication Token)
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Le agregamos detalles de la petición actual (IP, sesión, etc.)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 5. ¡Damos acceso oficial en el contexto de Spring! 🏛️
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception ex) {
            // Token expirado, malformado o inválido → lo ignoramos y dejamos pasar la petición.
            // Los endpoints públicos seguirán funcionando; los protegidos serán bloqueados por Spring Security.
            log.warn("Token JWT inválido o expirado en la petición [{}]: {}", request.getRequestURI(), ex.getMessage());
        }

        // 6. Pase lo que pase, dejamos que la petición siga su camino
        filterChain.doFilter(request, response);
    }
}

