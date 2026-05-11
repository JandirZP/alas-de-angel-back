package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.AntecedentesQuirurgicosDto;
import com.gestionpacientes.entity.AntecedentesQEntity;
import com.gestionpacientes.entity.HistoriaClinicaEntity;
import com.gestionpacientes.repository.AntecedentesQRepository;
import com.gestionpacientes.repository.HistoriaClinicaRepository;
import com.gestionpacientes.service.AntecedentesQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AntecedentesQServiceImpl implements AntecedentesQService {

    @Autowired
    private AntecedentesQRepository repositorio;

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Override
    public List<AntecedentesQEntity> findAll() {
        return repositorio.findAll();
    }

    @Override
    public AntecedentesQEntity findById(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El antecedente quirurgico no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AntecedentesQuirurgicosDto> buscarQuirurgicosPorCodigoHC(Long idHC) {
        return repositorio.findByHistoriaClinica_Codigo(idHC).stream()
                .map(q -> AntecedentesQuirurgicosDto.builder()
                        .id(q.getIdOperacion())
                        .historiaClinicaId(idHC)
                        .nombre(q.getNombreOperacion())
                        .fecha(q.getFechaOperacion())
                        .huboComplicaciones(q.getHuboComplicaciones())
                        .observaciones(q.getObservaciones())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AntecedentesQuirurgicosDto add(AntecedentesQuirurgicosDto dto, Long idHC) {
        HistoriaClinicaEntity historiaClinica = historiaClinicaRepository.findById(idHC)
                .orElseThrow(() -> new RuntimeException("La historia clinica no existe"));
        AntecedentesQEntity obj = new AntecedentesQEntity();
        obj.setHistoriaClinica(historiaClinica);
        obj.setNombreOperacion(dto.getNombre());
        obj.setFechaOperacion(dto.getFecha());
        obj.setHuboComplicaciones(dto.isHuboComplicaciones());
        obj.setObservaciones(dto.getObservaciones());
        repositorio.save(obj);
        return AntecedentesQuirurgicosDto.builder()
                .id(obj.getIdOperacion())
                .historiaClinicaId(idHC)
                .nombre(obj.getNombreOperacion())
                .fecha(obj.getFechaOperacion())
                .huboComplicaciones(obj.getHuboComplicaciones())
                .observaciones(obj.getObservaciones())
                .build();
    }

    @Override
    @Transactional
    public AntecedentesQuirurgicosDto update(AntecedentesQuirurgicosDto dto, Long idHC, Long idOperacion) {
        AntecedentesQEntity objAntQ = repositorio.findById(idOperacion)
                .orElseThrow(() -> new RuntimeException("El antecedente quirurgico no existe"));

        if (!objAntQ.getHistoriaClinica().getCodigo().equals(idHC)) {
            throw new RuntimeException("El antecedente quirurgico no pertenece a la historia clinica");
        }
        objAntQ.setNombreOperacion(dto.getNombre());
        objAntQ.setFechaOperacion(dto.getFecha());
        objAntQ.setHuboComplicaciones(dto.isHuboComplicaciones());
        objAntQ.setObservaciones(dto.getObservaciones());
        repositorio.save(objAntQ);
        return AntecedentesQuirurgicosDto.builder()
                .id(objAntQ.getIdOperacion())
                .historiaClinicaId(idHC)
                .nombre(objAntQ.getNombreOperacion())
                .fecha(objAntQ.getFechaOperacion())
                .huboComplicaciones(objAntQ.getHuboComplicaciones())
                .observaciones(objAntQ.getObservaciones())
                .build();
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repositorio.deleteById(id);
    }
}
