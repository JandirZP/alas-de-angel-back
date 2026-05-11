package com.gestionpacientes.service;

import com.gestionpacientes.entity.HistorialDrogasEntity;

import java.util.List;

import com.gestionpacientes.dto.HistorialDrogasDto;

public interface HistorialDrogasService {

    List<HistorialDrogasEntity> findAll();

    HistorialDrogasEntity findById(Long id);

    HistorialDrogasDto add(HistorialDrogasEntity obj, Long idHC);

    HistorialDrogasDto update(HistorialDrogasEntity obj, Long idHC, Long idDrogas);

    void deleteById(Long id);

    List<HistorialDrogasDto> buscarDrogasPorCodigoHC(Long idHC);
}
