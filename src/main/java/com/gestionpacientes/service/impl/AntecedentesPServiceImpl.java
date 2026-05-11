package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.AntecedentesPatologicosDto;
import com.gestionpacientes.entity.AntecedentesPEntity;
import com.gestionpacientes.entity.HistoriaClinicaEntity;
import com.gestionpacientes.repository.AntecedentesPRepository;
import com.gestionpacientes.repository.HistoriaClinicaRepository;
import com.gestionpacientes.service.AntecedentesPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AntecedentesPServiceImpl implements AntecedentesPService {

    @Autowired
    private AntecedentesPRepository repositorio;

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Override
    public List<AntecedentesPEntity> findAll() {
        return repositorio.findAll();
    }

    @Override
    public AntecedentesPEntity findById(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El antecedente patologico no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AntecedentesPatologicosDto> buscarPatologiasPorCodigoHC(Long idHC) {
        return repositorio.findByHistoriaClinicaEntity_Codigo(idHC).stream()
                .map(p -> AntecedentesPatologicosDto.builder()
                        .id(p.getIdPatologia())
                        .historiaClinicaId(idHC)
                        .nombre(p.getNombreEnfermedad())
                        .fechaDiagnostico(p.getFechaDiagnostico())
                        .enTratamiento(p.getEstaEnTratamiento())
                        .observaciones(p.getObservaciones())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public AntecedentesPatologicosDto add(AntecedentesPatologicosDto dto, Long idHC) {
        HistoriaClinicaEntity historiaClinica = historiaClinicaRepository.findById(idHC)
                .orElseThrow(() -> new RuntimeException("La historia clinica no existe"));

        AntecedentesPEntity obj = new AntecedentesPEntity();
        obj.setHistoriaClinicaEntity(historiaClinica);
        obj.setNombreEnfermedad(dto.getNombre());
        obj.setFechaDiagnostico(dto.getFechaDiagnostico());
        obj.setEstaEnTratamiento(dto.isEnTratamiento());
        obj.setObservaciones(dto.getObservaciones());

        repositorio.save(obj);
        return AntecedentesPatologicosDto.builder()
                .id(obj.getIdPatologia())
                .historiaClinicaId(idHC)
                .nombre(obj.getNombreEnfermedad())
                .fechaDiagnostico(obj.getFechaDiagnostico())
                .enTratamiento(obj.getEstaEnTratamiento())
                .observaciones(obj.getObservaciones())
                .build();
    }

    @Override
    @Transactional
    public AntecedentesPatologicosDto update(AntecedentesPatologicosDto dto, Long idHC, Long idPatologia) {
        AntecedentesPEntity objPatologia = repositorio.findById(idPatologia)
                .orElseThrow(() -> new RuntimeException("El antecedente patologico no existe"));

        if (!objPatologia.getHistoriaClinicaEntity().getCodigo().equals(idHC)) {
            throw new RuntimeException("El antecedente patologico no pertenece a la historia clinica");
        }
        objPatologia.setNombreEnfermedad(dto.getNombre());
        objPatologia.setFechaDiagnostico(dto.getFechaDiagnostico());
        objPatologia.setEstaEnTratamiento(dto.isEnTratamiento());
        objPatologia.setObservaciones(dto.getObservaciones());
        repositorio.save(objPatologia);
        return AntecedentesPatologicosDto.builder()
                .id(objPatologia.getIdPatologia())
                .historiaClinicaId(idHC)
                .nombre(objPatologia.getNombreEnfermedad())
                .fechaDiagnostico(objPatologia.getFechaDiagnostico())
                .enTratamiento(objPatologia.getEstaEnTratamiento())
                .observaciones(objPatologia.getObservaciones())
                .build();
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repositorio.deleteById(id);
    }
}
