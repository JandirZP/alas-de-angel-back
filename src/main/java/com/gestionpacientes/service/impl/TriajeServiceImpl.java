package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.TriajeDto;
import com.gestionpacientes.entity.CitaEntity;
import com.gestionpacientes.entity.TriajeEntity;
import com.gestionpacientes.entity.UsuarioEntity;
import com.gestionpacientes.repository.CitaRepository;
import com.gestionpacientes.repository.TriajeRepository;
import com.gestionpacientes.repository.UsuarioRepository;
import com.gestionpacientes.service.TriajeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class TriajeServiceImpl implements TriajeService {
    @Autowired
    private TriajeRepository triajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<TriajeEntity> findAll() {
        return triajeRepository.findAll();
    }

    @Override
    public List<TriajeEntity> findAllCustom() {
        return triajeRepository.findAllCustom();
    }

    @Override
    public TriajeEntity findById(@PathVariable Long id) {
        return triajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El triaje no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TriajeDto> buscarPorCita(Long idCita) {
        return triajeRepository.findByCita_IdCita(idCita)
                .map(triaje -> TriajeDto.builder()
                        .idTriaje(triaje.getIdTriaje())
                        .idCita(triaje.getCita().getIdCita())
                        .idEnfermera(triaje.getEnfermera().getIdUsuario())
                        .nombreEnfermera(triaje.getEnfermera().getNombres())
                        .apellidoEnfermera(triaje.getEnfermera().getApellidoPaterno() + " "
                                + triaje.getEnfermera().getApellidoMaterno())
                        .idPaciente(triaje.getCita().getPaciente().getIdUsuario())
                        .nombrePaciente(triaje.getCita().getPaciente().getNombres())
                        .apellidoPaciente(triaje.getCita().getPaciente().getApellidoPaterno() + " "
                                + triaje.getCita().getPaciente().getApellidoMaterno())
                        .sexoPaciente(triaje.getCita().getPaciente().getSexo())
                        .fechaHora(triaje.getFechaHora())
                        .peso(triaje.getPeso())
                        .altura(triaje.getAltura())
                        .presionArterial(triaje.getPresionArterial())
                        .temperatura(triaje.getTemperatura())
                        .tieneFiebre(triaje.getTieneFiebre())
                        .fechaUltimaRegla(triaje.getFechaUltimaRegla())
                        .estaEmbarazada(triaje.getEstaEmbarazada())
                        .semanasGestacion(triaje.getSemanasGestacion())
                        .estado(triaje.getEstado())
                        .build());
    }

    @Override
    @Transactional
    public TriajeDto add(TriajeEntity obj) {
        Long idEnfermera = obj.getEnfermera().getIdUsuario();
        UsuarioEntity enfermeraExiste = usuarioRepository.findById(idEnfermera)
                .orElseThrow(() -> new RuntimeException("La enfermera no existe"));
        obj.setEnfermera(enfermeraExiste);

        Long idCita = obj.getCita().getIdCita();
        CitaEntity citaExiste = citaRepository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("La cita no existe"));
        obj.setCita(citaExiste);

        obj.setEstado(true);

        TriajeEntity triajeGuardado = triajeRepository.save(obj);

        return TriajeDto.builder()
                .idCita(triajeGuardado.getCita().getIdCita())
                .idEnfermera(triajeGuardado.getEnfermera().getIdUsuario())
                .fechaHora(triajeGuardado.getFechaHora())
                .peso(triajeGuardado.getPeso())
                .altura(triajeGuardado.getAltura())
                .presionArterial(triajeGuardado.getPresionArterial())
                .temperatura(triajeGuardado.getTemperatura())
                .tieneFiebre(triajeGuardado.getTieneFiebre())
                .fechaUltimaRegla(triajeGuardado.getFechaUltimaRegla())
                .estaEmbarazada(triajeGuardado.getEstaEmbarazada())
                .semanasGestacion(triajeGuardado.getSemanasGestacion())
                .estado(triajeGuardado.getEstado())
                .build();

    }

    @Override
    public TriajeEntity update(TriajeEntity obj, Long id) {
        TriajeEntity objTriaje = triajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El triaje no existe"));

        BeanUtils.copyProperties(obj, objTriaje, "idTriaje");

        return triajeRepository.save(objTriaje);
    }

    @Override
    public TriajeEntity delete(Long id) {
        TriajeEntity objTriaje = triajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El triaje no existe"));

        objTriaje.setEstado(false);
        return triajeRepository.save(objTriaje);
    }

    @Override
    public TriajeEntity enable(Long id) {
        TriajeEntity objTriaje = triajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El triaje no existe"));

        objTriaje.setEstado(true);
        return triajeRepository.save(objTriaje);
    }
}
