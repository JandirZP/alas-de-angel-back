package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.EspecialidadesDto;
import com.gestionpacientes.entity.EspecialidadEntity;
import com.gestionpacientes.repository.EspecialidadRepository;
import com.gestionpacientes.repository.UsuarioRepository;
import com.gestionpacientes.service.EspecialidadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    @Autowired
    private EspecialidadRepository repositorio;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<EspecialidadEntity> findAll() {
        return repositorio.findAll();
    }

    @Override
    public List<EspecialidadesDto> findAllCustom() {
        return repositorio.findAllCustom().stream()
                .map(e -> EspecialidadesDto.builder()
                        .codigo(e.getCodigo())
                        .nombre(e.getNombre())
                        .descripcion(e.getDescripcion())
                        .estado(e.getEstado())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public EspecialidadEntity findById(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("La especialidad no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspecialidadesDto> buscarPorMedico(Long idUsuario) {
        var medicoEncontrado = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("El medico no existe"));
        var especialidadEntidad = repositorio.findByMedicos_IdUsuario(medicoEncontrado.getIdUsuario());
        return especialidadEntidad.stream()
                .map(e -> {
                    return EspecialidadesDto.builder()
                            .codigo(e.getCodigo())
                            .nombre(e.getNombre())
                            .descripcion(e.getDescripcion())
                            .estado(e.getEstado())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public EspecialidadEntity add(EspecialidadEntity obj) {
        return repositorio.save(obj);
    }

    @Override
    public EspecialidadEntity update(EspecialidadEntity obj, Long id) {
        EspecialidadEntity objUpdate = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("La especialidad no existe"));
        BeanUtils.copyProperties(obj, objUpdate, "codigo");
        return repositorio.save(objUpdate);
    }

    @Override
    public EspecialidadEntity delete(Long id) {
        EspecialidadEntity obj = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("La especialidad no existe"));
        obj.setEstado(false);
        return repositorio.save(obj);
    }

    @Override
    public EspecialidadEntity enable(Long id) {
        EspecialidadEntity obj = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("La especialidad no existe"));
        obj.setEstado(true);
        return repositorio.save(obj);
    }
}
