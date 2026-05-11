package com.gestionpacientes.service;

import com.gestionpacientes.dto.UsuarioDto;
import com.gestionpacientes.dto.UsuarioProfileDto;
import com.gestionpacientes.entity.UsuarioEntity;

import com.gestionpacientes.dto.EspecialidadesDto;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<EspecialidadesDto> updateEspecialidades(Long idUsuario, List<EspecialidadesDto> especialidadesDtoList);

    List<UsuarioEntity> findAll();

    List<UsuarioEntity> findAllCustom();

    List<UsuarioEntity> findByRoles_Nombre(String nombre);

    List<UsuarioDto> buscarPorRolActivo(String nombreRol);

    Optional<UsuarioEntity> findByCorreo(String correo);

    Optional<UsuarioDto> findPatientByDocumento(String documento);

    UsuarioEntity findById(Long id);

    UsuarioEntity add(UsuarioEntity obj);

    UsuarioEntity registrarPaciente(com.gestionpacientes.dto.RegistroPacienteDto dto);

    UsuarioEntity update(Long id, UsuarioProfileDto dto);

    UsuarioEntity delete(Long id);

    UsuarioEntity enable(Long id);

    List<UsuarioDto> findMedicosByEspecialidad(Long codigoEspecialidad);
}
