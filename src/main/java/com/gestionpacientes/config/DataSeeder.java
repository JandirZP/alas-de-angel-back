package com.gestionpacientes.config;

import com.gestionpacientes.entity.*;
import com.gestionpacientes.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

        private final UsuarioRepository usuarioRepository;
        private final RolRepository rolRepository;
        private final EspecialidadRepository especialidadRepository;
        private final NivelProfesionalRepository nivelProfesionalRepository;
        private final TipoDocumentoRepository tipoDocumentoRepository;
        private final UbigeoRepository ubigeoRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        @Transactional
        public void run(String... args) throws Exception {
                // Si la tabla esta vacia:
                if (usuarioRepository.count() == 0) {
                        System.out.println("Inicializando la carga de usuarios de prueba...");
                        cargarUsuarios();
                        System.out.println("Carga de usuarios finalizada.");
                }
        }

        private void cargarUsuarios() {
                String passwordEncriptada = passwordEncoder.encode("123456");

                // 1. Obtener catálogos base (Asume que ya existen en SQL)
                TipoDocumentoEntity dni = tipoDocumentoRepository.findById(1L)
                                .orElseThrow(() -> new RuntimeException("No se encontró el TipoDoc DNI"));
                UbigeoEntity ubigeoChachapoyas = ubigeoRepository.findById(10101L)
                                .orElseThrow(() -> new RuntimeException("No se encontró el Ubigeo"));

                // 2. Obtener Roles
                RolEntity rolPaciente = rolRepository.findByNombre("Paciente") // Asegúratede tener este método en el
                                // repository
                                .orElseThrow(() -> new RuntimeException("No se encontró el Rol Paciente"));
                RolEntity rolMedico = rolRepository.findByNombre("Médico")
                                .orElseThrow(() -> new RuntimeException("No se encontró el Rol Médico"));
                RolEntity rolEnfermera = rolRepository.findByNombre("Enfermera")
                                .orElseThrow(() -> new RuntimeException("No se encontró el Rol Enfermera"));
                RolEntity rolAdministrador = rolRepository.findByNombre("Administrador")
                                .orElseThrow(() -> new RuntimeException("No se encontró el RolAdministrador"));

                // 3. Obtener Nivel Profesional y Especialidad para Médicos
                NivelProfesionalEntity nivMedicoGeneral = nivelProfesionalRepository.findById(2L)
                                .orElseThrow(() -> new RuntimeException(
                                                "No se encontró el Nivel Profesional Médico General"));
                EspecialidadEntity espCardiologia = especialidadRepository.findById(1L) // Asumiendo ID 100 por tu
                                // script SQL
                                .orElseThrow(() -> new RuntimeException("No se encontró la EspecialidadCardiología"));

                // ==========================================
                // CREACIÓN DE PACIENTES
                // ==========================================
                UsuarioEntity paciente1 = UsuarioEntity.builder()
                                .nombres("Carlos")
                                .apellidoPaterno("Mendoza")
                                .apellidoMaterno("Vargas")
                                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                                .tipoDocumentoEntity(dni)
                                .numeroDocumento("11223344")
                                .sexo(true) // Masculino
                                .celular("987654321")
                                .contactoEmergencia("Ana Vargas")
                                .celularContacto("987111222")
                                .direccion("Av. Los Faisanes 123")
                                .ubigeoEntity(ubigeoChachapoyas)
                                .paisOrigen("PE")
                                .nombreUsuario("cmendoza")
                                .correo("cmendoza@clinica.com")
                                .password(passwordEncriptada)
                                .estado(true)
                                .roles(Set.of(rolPaciente))
                                .build();
                usuarioRepository.save(paciente1);

                UsuarioEntity paciente2 = UsuarioEntity.builder()
                                .nombres("Alvaro")
                                .apellidoPaterno("Reyes")
                                .apellidoMaterno("Lopez")
                                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                                .tipoDocumentoEntity(dni)
                                .numeroDocumento("45369872")
                                .sexo(true) // Masculino
                                .celular("987654321")
                                .contactoEmergencia("Ana Vargas")
                                .celularContacto("987111222")
                                .direccion("Av. Los Faisanes 123")
                                .ubigeoEntity(ubigeoChachapoyas)
                                .paisOrigen("PE")
                                .nombreUsuario("areyes")
                                .correo("areyes@clinica.com")
                                .password(passwordEncriptada)
                                .estado(true)
                                .roles(Set.of(rolPaciente))
                                .build();
                usuarioRepository.save(paciente2);

                // ==========================================
                // CREACIÓN DE MÉDICOS
                // ==========================================
                UsuarioEntity medico1 = UsuarioEntity.builder()
                                .nombres("Luis")
                                .apellidoPaterno("Fernandez")
                                .apellidoMaterno("Rios")
                                .fechaNacimiento(LocalDate.of(1980, 3, 22))
                                .tipoDocumentoEntity(dni)
                                .numeroDocumento("44332211")
                                .sexo(true)
                                .celular("999888777")
                                .contactoEmergencia("Carmen Rios")
                                .celularContacto("999111333")
                                .direccion("Calle Las Begonias 456")
                                .ubigeoEntity(ubigeoChachapoyas)
                                .paisOrigen("PE")
                                .nombreUsuario("lfernandez")
                                .correo("medico1@clinica.com")
                                .password(passwordEncriptada)
                                .nivelProfesionalEntity(nivMedicoGeneral) // Solo el médico lo tiene
                                .estado(true)
                                .roles(Set.of(rolMedico))
                                .especialidades(Set.of(espCardiologia)) // Solo el médico lo tiene
                                .build();
                usuarioRepository.save(medico1);

                UsuarioEntity medico2 = UsuarioEntity.builder()
                                .nombres("Ana")
                                .apellidoPaterno("Gutierrez")
                                .apellidoMaterno("Lopez")
                                .fechaNacimiento(LocalDate.of(1985, 7, 10))
                                .tipoDocumentoEntity(dni)
                                .numeroDocumento("55667788")
                                .sexo(false)
                                .celular("988777666")
                                .contactoEmergencia("Juan Gutierrez")
                                .celularContacto("977666555")
                                .direccion("Av. Los Pinos 123")
                                .ubigeoEntity(ubigeoChachapoyas)
                                .paisOrigen("PE")
                                .nombreUsuario("agutierrez")
                                .correo("medico2@clinica.com")
                                .password(passwordEncriptada)
                                .nivelProfesionalEntity(nivMedicoGeneral)
                                .estado(true)
                                .roles(Set.of(rolMedico))
                                .especialidades(Set.of(espCardiologia))
                                .build();

                usuarioRepository.save(medico2);

                UsuarioEntity medico3 = UsuarioEntity.builder()
                                .nombres("Carlos")
                                .apellidoPaterno("Ramirez")
                                .apellidoMaterno("Vega")
                                .fechaNacimiento(LocalDate.of(1978, 11, 5))
                                .tipoDocumentoEntity(dni)
                                .numeroDocumento("66778899")
                                .sexo(true)
                                .celular("977555444")
                                .contactoEmergencia("Maria Vega")
                                .celularContacto("966444333")
                                .direccion("Jr. Amazonas 789")
                                .ubigeoEntity(ubigeoChachapoyas)
                                .paisOrigen("PE")
                                .nombreUsuario("cramirez")
                                .correo("medico3@clinica.com")
                                .password(passwordEncriptada)
                                .nivelProfesionalEntity(nivMedicoGeneral)
                                .estado(true)
                                .roles(Set.of(rolMedico))
                                .especialidades(Set.of(espCardiologia))
                                .build();

                usuarioRepository.save(medico3);

                // ==========================================
                // CREACIÓN DE ENFERMERAS
                // ==========================================
                UsuarioEntity enfermera1 = UsuarioEntity.builder()
                                .nombres("Maria")
                                .apellidoPaterno("Gomez")
                                .apellidoMaterno("Lopez")
                                .fechaNacimiento(LocalDate.of(1992, 11, 10))
                                .tipoDocumentoEntity(dni)
                                .numeroDocumento("55667788")
                                .sexo(false) // Femenino
                                .celular("911222333")
                                .contactoEmergencia("Pedro Gomez")
                                .celularContacto("911333444")
                                .direccion("Jr. Huallaga 789")
                                .ubigeoEntity(ubigeoChachapoyas)
                                .paisOrigen("PE")
                                .nombreUsuario("mgomez")
                                .correo("enfermera1@clinica.com")
                                .password(passwordEncriptada)
                                .estado(true)
                                .roles(Set.of(rolEnfermera))
                                .build();
                usuarioRepository.save(enfermera1);

                UsuarioEntity enfermera2 = UsuarioEntity.builder()
                                .nombres("Rosa")
                                .apellidoPaterno("Fernandez")
                                .apellidoMaterno("Quispe")
                                .fechaNacimiento(LocalDate.of(1990, 4, 18))
                                .tipoDocumentoEntity(dni)
                                .numeroDocumento("66778899")
                                .sexo(false)
                                .celular("922333444")
                                .contactoEmergencia("Luis Fernandez")
                                .celularContacto("933444555")
                                .direccion("Av. Libertad 456")
                                .ubigeoEntity(ubigeoChachapoyas)
                                .paisOrigen("PE")
                                .nombreUsuario("rfernandez")
                                .correo("enfermera2@clinica.com")
                                .password(passwordEncriptada)
                                .estado(true)
                                .roles(Set.of(rolEnfermera))
                                .build();

                usuarioRepository.save(enfermera2);

                UsuarioEntity enfermera3 = UsuarioEntity.builder()
                                .nombres("Lucia")
                                .apellidoPaterno("Torres")
                                .apellidoMaterno("Salazar")
                                .fechaNacimiento(LocalDate.of(1995, 8, 2))
                                .tipoDocumentoEntity(dni)
                                .numeroDocumento("77889900")
                                .sexo(false)
                                .celular("944555666")
                                .contactoEmergencia("Carmen Salazar")
                                .celularContacto("955666777")
                                .direccion("Calle Amazonas 321")
                                .ubigeoEntity(ubigeoChachapoyas)
                                .paisOrigen("PE")
                                .nombreUsuario("ltorres")
                                .correo("enfermera3@clinica.com")
                                .password(passwordEncriptada)
                                .estado(true)
                                .roles(Set.of(rolEnfermera))
                                .build();

                usuarioRepository.save(enfermera3);

                // ==========================================
                // CREACIÓN DE ADMINISTRADOR
                // ==========================================
                UsuarioEntity administrador = UsuarioEntity.builder()
                                .nombres("Angel")
                                .apellidoPaterno("Zavala")
                                .apellidoMaterno("Paredes")
                                .fechaNacimiento(LocalDate.of(1995, 8, 2))
                                .tipoDocumentoEntity(dni)
                                .numeroDocumento("77889900")
                                .sexo(false)
                                .celular("944555666")
                                .contactoEmergencia("Carmen Salazar")
                                .celularContacto("955666777")
                                .direccion("Calle Amazonas 321")
                                .ubigeoEntity(ubigeoChachapoyas)
                                .paisOrigen("PE")
                                .nombreUsuario("admin")
                                .correo("admin@clinica.com")
                                .password(passwordEncriptada)
                                .estado(true)
                                .roles(Set.of(rolAdministrador))
                                .build();

                usuarioRepository.save(administrador);
        }
}
