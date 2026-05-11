package com.gestionpacientes.service;

import com.gestionpacientes.entity.AntecedentesQEntity;

import java.util.List;

import com.gestionpacientes.dto.AntecedentesQuirurgicosDto;

public interface AntecedentesQService {

    List<AntecedentesQEntity> findAll();

    AntecedentesQEntity findById(Long id);

    AntecedentesQuirurgicosDto add(AntecedentesQuirurgicosDto obj, Long idHC);

    AntecedentesQuirurgicosDto update(AntecedentesQuirurgicosDto obj, Long idHC, Long idOperacion);

    void deleteById(Long id);

    List<AntecedentesQuirurgicosDto> buscarQuirurgicosPorCodigoHC(Long idHC);
}
