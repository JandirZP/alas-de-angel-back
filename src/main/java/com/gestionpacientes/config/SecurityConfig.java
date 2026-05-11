package com.gestionpacientes.config;

import com.gestionpacientes.service.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // 1. Desactivamos CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 2. Sin
                                                                                                              // sesiones
                                                                                                              // en
                                                                                                              // memoria
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarioRest", "/usuarioRest/login", "/error", "/api/sedes/activos",
                                "/especialidad/custom", "/usuarioRest/por-rol-activo/**",
                                "/usuarioRest/medicos/especialidad/**", "/ubigeoRest/**", 
                                "/usuarioRest/pacientes/registro", "/usuarioRest/upload/**", 
                                "/usuarioRest/paciente/documento/**")
                        .permitAll()
                        .anyRequest().authenticated() // 3. Por ahora, ¡todo bloqueado!
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    // Necesitamos importar AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. ¿Quién puede entrar? (Tu Frontend)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));

        // 2. ¿Qué pueden hacer? (POST para login, GET para ver datos, etc.)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 3. ¿Qué datos pueden enviar en la cabecera? (Token, tipo de archivo)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        // 4. Permitir credenciales (cookies, headers de autorización)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplicamos esto a TODAS las rutas de la aplicación
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
