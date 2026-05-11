package com.gestionpacientes.service;

import com.gestionpacientes.dto.EspecialidadesDto;
import com.gestionpacientes.entity.EspecialidadEntity;

import java.util.List;

public interface EspecialidadService {

    List<EspecialidadEntity> findAll();

    List<EspecialidadEntity> findAllCustom();

    List<EspecialidadesDto> buscarPorMedico(Long idUsuario);

    EspecialidadEntity findById(Long id);

    EspecialidadEntity add(EspecialidadEntity obj);

    EspecialidadEntity update(EspecialidadEntity obj, Long id);

    EspecialidadEntity delete(Long id);

    EspecialidadEntity enable(Long id);
}
