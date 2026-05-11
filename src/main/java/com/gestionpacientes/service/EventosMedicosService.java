package com.gestionpacientes.service;

import com.gestionpacientes.dto.EventosMedicosDto;
import com.gestionpacientes.entity.EventosMedicosEntity;

import java.util.List;

public interface EventosMedicosService {
    List<EventosMedicosEntity> findAll();

    List<EventosMedicosEntity> findAllCustom();

    EventosMedicosEntity findById(Long id);

    EventosMedicosDto add(EventosMedicosEntity obj);

    EventosMedicosEntity update(EventosMedicosEntity obj, Long id);

    EventosMedicosEntity delete(Long id);

    EventosMedicosEntity enable(Long id);
}
