package com.gestionpacientes.service;

import com.gestionpacientes.dto.NivelProfesionalDto;
import com.gestionpacientes.entity.NivelProfesionalEntity;

import java.util.List;

public interface NivelProfesionalService {

    List<NivelProfesionalEntity> findAll();

    List<NivelProfesionalDto> findAllCustom();

    NivelProfesionalEntity findById(Long id);

    NivelProfesionalEntity add(NivelProfesionalEntity obj);

    NivelProfesionalEntity update(NivelProfesionalEntity obj, Long id);

    NivelProfesionalEntity delete(Long id);

    NivelProfesionalEntity enable(Long id);
}
