package com.gestionpacientes.service.security;

import com.gestionpacientes.entity.UsuarioEntity;
import com.gestionpacientes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UsuarioRepository repositorio;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // 1. Buscamos y desempaquetamos (si no está, lanza error automáticamente)
                UsuarioEntity usuario = repositorio.findByCorreo(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

                // Convertimos tus Roles (Entity) a Autoridades (Spring Security)
                // Asumimos que en BD tus roles se llaman "PACIENTE", "MEDICO", "ADMIN", etc.

                // 2. Construimos el UserDetails que Spring necesita
                List<GrantedAuthority> autoridades = usuario.getRoles().stream()
                                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                                .collect(Collectors.toList());

                return new User(
                                usuario.getCorreo(),
                                usuario.getPassword(),
                                usuario.getEstado(),
                                true,
                                true,
                                true,
                                autoridades);

        }
}
