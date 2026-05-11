package com.gestionpacientes.service;

import com.gestionpacientes.dto.TriajeDto;
import com.gestionpacientes.entity.TriajeEntity;

import java.util.List;
import java.util.Optional;

public interface TriajeService {

    List<TriajeEntity> findAll();

    List<TriajeEntity> findAllCustom();

    Optional<TriajeDto> buscarPorCita(Long idCita);

    TriajeEntity findById(Long id);

    TriajeDto add(TriajeEntity obj);

    TriajeEntity update(TriajeEntity obj, Long idTriaje);

    TriajeEntity delete(Long idTriaje);

    TriajeEntity enable(Long idTriaje);
}
