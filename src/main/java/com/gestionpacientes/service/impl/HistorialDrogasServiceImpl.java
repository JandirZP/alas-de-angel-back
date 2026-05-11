package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.HistorialDrogasDto;
import com.gestionpacientes.entity.HistorialDrogasEntity;
import com.gestionpacientes.entity.HistoriaClinicaEntity;
import com.gestionpacientes.repository.HistorialDrogasRepository;
import com.gestionpacientes.repository.HistoriaClinicaRepository;
import com.gestionpacientes.service.HistorialDrogasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialDrogasServiceImpl implements HistorialDrogasService {

    @Autowired
    private HistorialDrogasRepository repositorio;

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Override
    public List<HistorialDrogasEntity> findAll() {
        return repositorio.findAll();
    }

    @Override
    public HistorialDrogasEntity findById(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El historial de drogas no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialDrogasDto> buscarDrogasPorCodigoHC(Long idHC) {
        return repositorio.findByHistoriaClinica_Codigo(idHC).stream()
                .map(d -> HistorialDrogasDto.builder()
                        .id(d.getIdDrogas())
                        .historiaClinicaId(idHC)
                        .nombreDroga(d.getNombreDroga())
                        .frecuencia(d.getFrecuencia())
                        .observaciones(d.getObservaciones())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HistorialDrogasDto add(HistorialDrogasEntity obj, Long idHC) {
        HistoriaClinicaEntity historiaClinica = historiaClinicaRepository.findById(idHC)
                .orElseThrow(() -> new RuntimeException("La historia clinica no existe"));
        obj.setHistoriaClinica(historiaClinica);
        repositorio.save(obj);
        return HistorialDrogasDto.builder()
                .id(obj.getIdDrogas())
                .historiaClinicaId(idHC)
                .nombreDroga(obj.getNombreDroga())
                .frecuencia(obj.getFrecuencia())
                .observaciones(obj.getObservaciones())
                .build();
    }

    @Override
    @Transactional
    public HistorialDrogasDto update(HistorialDrogasEntity obj, Long idHC, Long idDrogas) {
        HistorialDrogasEntity objDrogas = repositorio.findById(idDrogas)
                .orElseThrow(() -> new RuntimeException("El historial de drogas no existe"));

        if (!objDrogas.getHistoriaClinica().getCodigo().equals(idHC)) {
            throw new RuntimeException("El historial de drogas no pertenece a la historia clinica");
        }
        objDrogas.setNombreDroga(obj.getNombreDroga());
        objDrogas.setFrecuencia(obj.getFrecuencia());
        objDrogas.setObservaciones(obj.getObservaciones());
        repositorio.save(objDrogas);
        return HistorialDrogasDto.builder()
                .id(objDrogas.getIdDrogas())
                .historiaClinicaId(idHC)
                .nombreDroga(objDrogas.getNombreDroga())
                .frecuencia(objDrogas.getFrecuencia())
                .observaciones(objDrogas.getObservaciones())
                .build();
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repositorio.deleteById(id);
    }
}
