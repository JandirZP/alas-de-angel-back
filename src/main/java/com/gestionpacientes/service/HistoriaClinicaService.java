package com.gestionpacientes.service;

import com.gestionpacientes.dto.HistoriaClinicaDto;
import com.gestionpacientes.dto.TratamientoDto;
import com.gestionpacientes.entity.HistoriaClinicaEntity;

import java.util.List;
import java.util.Optional;

public interface HistoriaClinicaService {

    List<HistoriaClinicaEntity> findAll();

    List<HistoriaClinicaDto> findActivas();

    Optional<TratamientoDto> obtenerUltimoTratamiento(Long idPaciente);

    Optional<HistoriaClinicaDto> buscarPorDocumento(String numeroDocumento);

    Optional<HistoriaClinicaDto> buscarPorIdPaciente(Long idPaciente);

    HistoriaClinicaEntity findById(Long id);

    Optional<HistoriaClinicaDto> findDtoCompletoById(Long id);

    HistoriaClinicaDto add(HistoriaClinicaEntity obj);

    HistoriaClinicaDto update(HistoriaClinicaEntity obj, Long id);

    HistoriaClinicaEntity delete(Long id);

    HistoriaClinicaEntity enable(Long id);
}
