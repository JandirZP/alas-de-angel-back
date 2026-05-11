package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.NivelProfesionalDto;
import com.gestionpacientes.entity.NivelProfesionalEntity;
import com.gestionpacientes.repository.NivelProfesionalRepository;
import com.gestionpacientes.service.NivelProfesionalService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NivelProfesionalServiceImpl implements NivelProfesionalService {

    @Autowired
    private NivelProfesionalRepository repositorio;

    @Override
    public List<NivelProfesionalEntity> findAll() {
        return repositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NivelProfesionalDto> findAllCustom() {
        var listaNiveles = repositorio.findAllCustom();
        return listaNiveles.stream().map(nivel -> NivelProfesionalDto.builder()
                .idNivelProfesionalDto(nivel.getIdNivelProfesional())
                .nombreDto(nivel.getNombre())
                .estadoDto(nivel.getEstado())
                .build()).collect(Collectors.toList());
    }

    @Override
    public NivelProfesionalEntity findById(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El nivel profesional no existe"));
    }

    @Override
    public NivelProfesionalEntity add(NivelProfesionalEntity obj) {
        return repositorio.save(obj);
    }

    @Override
    public NivelProfesionalEntity update(NivelProfesionalEntity obj, Long id) {
        NivelProfesionalEntity objUpdate = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El nivel profesional no existe"));
        BeanUtils.copyProperties(obj, objUpdate, "idNivelProfesional");
        return repositorio.save(objUpdate);
    }

    @Override
    public NivelProfesionalEntity delete(Long id) {
        NivelProfesionalEntity objNiv = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El nivel profesional no existe"));
        objNiv.setEstado(false);
        return repositorio.save(objNiv);
    }

    @Override
    public NivelProfesionalEntity enable(Long id) {
        NivelProfesionalEntity objNiv = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El nivel profesional no existe"));
        objNiv.setEstado(true);
        return repositorio.save(objNiv);
    }

}
