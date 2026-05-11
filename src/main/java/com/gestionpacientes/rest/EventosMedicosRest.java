package com.gestionpacientes.rest;

import com.gestionpacientes.dto.EventosMedicosDto;
import com.gestionpacientes.entity.EventosMedicosEntity;
import com.gestionpacientes.service.EventosMedicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventosmedicosRest")
public class EventosMedicosRest {

    @Autowired
    private EventosMedicosService servicio;

    @GetMapping
    public List<EventosMedicosEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/custom")
    public List<EventosMedicosEntity> findAllCustom() {
        return servicio.findAllCustom();
    }

    @GetMapping("/{id}")
    public EventosMedicosEntity findById(@PathVariable Long id) {
        return servicio.findById(id);
    }

    @PostMapping
    public ResponseEntity<EventosMedicosDto> add(@RequestBody EventosMedicosEntity obj) {
        EventosMedicosDto eventoCreado = servicio.add(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoCreado);
    }

    @PutMapping("/{id}")
    public EventosMedicosEntity update(@RequestBody EventosMedicosEntity obj, @PathVariable Long id) {
        return servicio.update(obj, id);
    }

    @DeleteMapping("/{id}")
    public EventosMedicosEntity delete(@PathVariable Long id) {
        return servicio.delete(id);
    }

    @PutMapping("/enable/{id}")
    public EventosMedicosEntity enable(@PathVariable Long id) {
        return servicio.enable(id);
    }
}
