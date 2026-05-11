package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.EventosMedicosDto;
import com.gestionpacientes.entity.EventosMedicosEntity;
import com.gestionpacientes.entity.TriajeEntity;
import com.gestionpacientes.repository.EventosMedicosRepository;
import com.gestionpacientes.repository.HistoriaClinicaRepository;
import com.gestionpacientes.repository.TriajeRepository;
import com.gestionpacientes.service.EventosMedicosService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventosMedicosServiceImpl implements EventosMedicosService {

    @Autowired
    private EventosMedicosRepository eventosMedicosRepository;

    @Autowired
    private TriajeRepository triajeRepository;

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Override
    public List<EventosMedicosEntity> findAll() {
        return eventosMedicosRepository.findAll();
    }

    @Override
    public List<EventosMedicosEntity> findAllCustom() {
        return eventosMedicosRepository.findAllCustom();
    }

    @Override
    public EventosMedicosDto add(EventosMedicosEntity obj) {
        Long codigoTriaje = obj.getTriaje().getIdTriaje();
        TriajeEntity triaje = triajeRepository.findById(codigoTriaje)
                .orElseThrow(() -> new RuntimeException("no existe el triaje"));
        obj.setTriaje(triaje);
        obj.setEstado(true);
        obj.setFechaHora(LocalDateTime.now());
        EventosMedicosEntity evento = eventosMedicosRepository.save(obj);

        // Vincular automáticamente el Evento Médico a la Historia Clínica del paciente
        Long idPaciente = triaje.getCita().getPaciente().getIdUsuario();
        historiaClinicaRepository.findByPacienteEntity_IdUsuario(idPaciente)
                .ifPresent(hc -> {
                    hc.getEventosMedicos().add(evento);
                    historiaClinicaRepository.save(hc);
                });
        return EventosMedicosDto.builder()
                .idEventoMedico(evento.getIdEventoMedico())
                .tipoEvento(evento.getTipoEvento())
                .fechaHora(evento.getFechaHora())
                .descripcion(evento.getDescripcion())
                .diagnostico(evento.getDiagnostico())
                .medicamentos(evento.getMedicamentos())
                .recomendaciones(evento.getRecomendaciones())
                .dieta(evento.getDieta())
                .idTriaje(evento.getTriaje().getIdTriaje())
                .estado(evento.getEstado())
                .build();

    }

    @Override
    public EventosMedicosEntity findById(Long id) {
        return eventosMedicosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no existe el evento medico"));
    }

    @Override
    public EventosMedicosEntity update(EventosMedicosEntity obj, Long id) {
        EventosMedicosEntity objEv = eventosMedicosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no existe el evento medico"));
        BeanUtils.copyProperties(obj, objEv, "idEventoMedico");
        return eventosMedicosRepository.save(objEv);

    }

    @Override
    public EventosMedicosEntity delete(Long id) {
        EventosMedicosEntity objEv = eventosMedicosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no existe el evento medico"));
        objEv.setEstado(false);
        return eventosMedicosRepository.save(objEv);
    }

    @Override
    public EventosMedicosEntity enable(Long id) {
        EventosMedicosEntity objEv = eventosMedicosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no existe el evento medico"));
        objEv.setEstado(true);
        return eventosMedicosRepository.save(objEv);
    }
}
