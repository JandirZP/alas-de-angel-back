package com.gestionpacientes.service;

import com.gestionpacientes.dto.AntecedentesPatologicosDto;
import com.gestionpacientes.entity.AntecedentesPEntity;

import java.util.List;

public interface AntecedentesPService {

    List<AntecedentesPEntity> findAll();

    AntecedentesPEntity findById(Long id);

    AntecedentesPatologicosDto add(AntecedentesPatologicosDto obj, Long idHC);

    AntecedentesPatologicosDto update(AntecedentesPatologicosDto obj, Long idHC, Long idPatologia);

    void deleteById(Long id);

    List<AntecedentesPatologicosDto> buscarPatologiasPorCodigoHC(Long idHC);
}
