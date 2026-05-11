package com.gestionpacientes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestionpacientes.dto.SedesDto;
import com.gestionpacientes.repository.SedesRepository;
import com.gestionpacientes.service.SedesService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SedesSeviceImpl implements SedesService {

    @Autowired
    private SedesRepository sedesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SedesDto> findAllActivos() {
        return sedesRepository.findByEstado(true).stream()
                .map(s -> SedesDto.builder()
                        .idSedes(s.getIdSedes())
                        .nombre(s.getNombre())
                        .direccion(s.getDireccion())
                        .referencia(s.getReferencia())
                        .telefonoContacto(s.getTelefonoContacto())
                        .estado(s.getEstado())
                        .idUbigeo(s.getUbigeo() != null ? s.getUbigeo().getIdUbigeo() : null)
                        .departamento(s.getUbigeo() != null ? s.getUbigeo().getDepartamento() : null)
                        .provincia(s.getUbigeo() != null ? s.getUbigeo().getProvincia() : null)
                        .distrito(s.getUbigeo() != null ? s.getUbigeo().getDistrito() : null)
                        .build())
                .collect(Collectors.toList());
    }
}
