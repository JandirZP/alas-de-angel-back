package com.gestionpacientes.repository;

import com.gestionpacientes.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    // Todos los usuarios activos
    @Query("SELECT u FROM UsuarioEntity u WHERE u.estado = true")
    List<UsuarioEntity> findAllCustom();

    // Todos los usuarios de un solo rol
    List<UsuarioEntity> findByRoles_Nombre(String nombreDelRol);

    // Todos los usuarios activos de un solo rol
    List<UsuarioEntity> findByRoles_NombreAndEstadoTrue(String nombreDelRol);

    // Encontar por correo
    Optional<UsuarioEntity> findByCorreo(String correo);

    // Busca usuarios activos que tengan una especialidad con un código específico
    List<UsuarioEntity> findByEspecialidades_CodigoAndEstadoTrue(Long codigoEspecialidad);

    // Busca pacientes específicamente por documento y rol
    Optional<UsuarioEntity> findByNumeroDocumentoAndRoles_Nombre(String numeroDocumento, String nombreRol);

    // Encontrar por nombre de usuario
    Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario);

}
