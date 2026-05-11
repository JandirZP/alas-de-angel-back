package com.gestionpacientes.service;

import com.gestionpacientes.dto.CitaDto;
import com.gestionpacientes.entity.CitaEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CitaService {

    List<CitaEntity> findAll();

    List<CitaDto> buscarTodasLasCitas();

    List<CitaEntity> findAllCustom();

    List<CitaDto> findByPacienteId(Long idPaciente);

    List<CitaDto> findAllCitasPacienteId(Long idPaciente);

    List<CitaDto> findByMedicoId(Long idMedico, LocalDateTime fechaHora);

    List<CitaDto> findAllActiveByMedicoId(Long idMedico);

    List<CitaDto> buscarCitasByDocumento(String documento);

    List<CitaDto> buscarCitasPorFecha(Date fechaInicio, Date fechaFin);

    CitaDto add(CitaEntity obj);

    Optional<CitaDto> buscarPorCodigo(Long idCita);

    CitaDto update(CitaEntity obj, Long id);

    String delete(Long id);

    String enable(Long id);
}
