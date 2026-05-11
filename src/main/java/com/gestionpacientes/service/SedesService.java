package com.gestionpacientes.service;

import com.gestionpacientes.dto.SedesDto;

import java.util.List;

public interface SedesService {
    List<SedesDto> findAllActivos();
}
