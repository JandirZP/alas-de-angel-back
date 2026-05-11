package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.AlergiasDto;
import com.gestionpacientes.entity.AlergiasEntity;
import com.gestionpacientes.entity.HistoriaClinicaEntity;
import com.gestionpacientes.repository.AlergiasRepository;
import com.gestionpacientes.service.AlergiasService;

import com.gestionpacientes.repository.HistoriaClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlergiasServiceImpl implements AlergiasService {

    @Autowired
    private AlergiasRepository repositorio;

    @Autowired
    private HistoriaClinicaRepository hcRepositorio;

    @Override
    public List<AlergiasEntity> findAll() {
        return repositorio.findAll();
    }

    @Override
    public AlergiasEntity findById(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("La alergia no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlergiasDto> buscarAlergiasPorIdHistoria(Long idHC) {
        return repositorio.findByHistoriaClinica_Codigo(idHC).stream()
                .map(a -> AlergiasDto.builder()
                        .id(a.getIdAlergia())
                        .historiaClinicaId(idHC)
                        .alergeno(a.getAlergeno())
                        .reaccion(a.getReaccion())
                        .observaciones(a.getObservaciones())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AlergiasDto add(AlergiasEntity obj, Long idHC) {
        // Obtenemos la historia clínica real para enlazarla
        HistoriaClinicaEntity hc = hcRepositorio.findById(idHC)
                .orElseThrow(() -> new RuntimeException("La historia clinica no existe"));
        obj.setHistoriaClinica(hc);

        AlergiasEntity objAlergia = repositorio.save(obj);
        return AlergiasDto.builder()
                .id(objAlergia.getIdAlergia())
                .historiaClinicaId(idHC)
                .alergeno(objAlergia.getAlergeno())
                .reaccion(objAlergia.getReaccion())
                .observaciones(objAlergia.getObservaciones())
                .build();
    }

    @Override
    @Transactional
    public AlergiasDto update(AlergiasEntity obj, Long idHC, Long idAlergia) {
        AlergiasEntity objAlergia = repositorio.findById(idAlergia)
                .orElseThrow(() -> new RuntimeException("La alergia no existe"));

        // Verificamos de forma opcional pero recomendada que la alergia pertenezca a la
        // historia
        if (!objAlergia.getHistoriaClinica().getCodigo().equals(idHC)) {
            throw new RuntimeException("La alergia no pertenece a la historia clinica especificada");
        }

        objAlergia.setAlergeno(obj.getAlergeno());
        objAlergia.setReaccion(obj.getReaccion());
        objAlergia.setObservaciones(obj.getObservaciones());
        repositorio.save(objAlergia);
        return AlergiasDto.builder()
                .id(objAlergia.getIdAlergia())
                .historiaClinicaId(idHC)
                .alergeno(objAlergia.getAlergeno())
                .reaccion(objAlergia.getReaccion())
                .observaciones(objAlergia.getObservaciones())
                .build();
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repositorio.deleteById(id);
    }
}
