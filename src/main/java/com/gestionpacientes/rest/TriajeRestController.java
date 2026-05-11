package com.gestionpacientes.rest;

import com.gestionpacientes.dto.TriajeDto;
import com.gestionpacientes.entity.TriajeEntity;
import com.gestionpacientes.service.TriajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/triajeRest")
public class TriajeRestController {

    @Autowired
    private TriajeService servicio;

    @GetMapping
    public List<TriajeEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/custom")
    public List<TriajeEntity> findAllCustom() {
        return servicio.findAllCustom();
    }

    @GetMapping("/buscarPorCita/{idCita}")
    public ResponseEntity<Optional<TriajeDto>> buscarPorCita(@PathVariable Long idCita) {
        Optional<TriajeDto> triaje = servicio.buscarPorCita(idCita);
        if (triaje.isPresent()) {
            return ResponseEntity.ok(triaje);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public TriajeEntity findById(@PathVariable Long id) {
        return servicio.findById(id);
    }

    @PostMapping
    public ResponseEntity<TriajeDto> add(@RequestBody TriajeEntity obj) {
        TriajeDto triajeCreado = servicio.add(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(triajeCreado);
    }

    @PutMapping("/{id}")
    public TriajeEntity update(@RequestBody TriajeEntity obj, @PathVariable Long id) {
        return servicio.update(obj, id);
    }

    @DeleteMapping("/{id}")
    public TriajeEntity delete(@PathVariable Long id) {
        return servicio.delete(id);
    }

    @PutMapping("/enable/{id}")
    public TriajeEntity enable(@PathVariable Long id) {
        return servicio.enable(id);
    }
}
