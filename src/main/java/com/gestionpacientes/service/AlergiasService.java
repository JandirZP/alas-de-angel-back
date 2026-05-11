package com.gestionpacientes.service;

import com.gestionpacientes.dto.AlergiasDto;
import com.gestionpacientes.entity.AlergiasEntity;

import java.util.List;

public interface AlergiasService {

    List<AlergiasEntity> findAll();

    AlergiasEntity findById(Long id);

    List<AlergiasDto> buscarAlergiasPorIdHistoria(Long idHC);

    AlergiasDto add(AlergiasEntity obj, Long idHC);

    AlergiasDto update(AlergiasEntity obj, Long idHC, Long idAlergia);

    void deleteById(Long id);
}
