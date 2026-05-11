package com.gestionpacientes.rest;

import com.gestionpacientes.dto.NivelProfesionalDto;
import com.gestionpacientes.entity.NivelProfesionalEntity;
import com.gestionpacientes.service.NivelProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nivelprofesionalRest")
public class NivelProfesionalRest {

    @Autowired
    private NivelProfesionalService servicio;

    @GetMapping
    public List<NivelProfesionalEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/custom")
    public ResponseEntity<List<NivelProfesionalDto>> findAllCustom() {
        List<NivelProfesionalDto> listaNivelesActivos = servicio.findAllCustom();
        if (listaNivelesActivos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaNivelesActivos);
    }

    @GetMapping("/{id}")
    public NivelProfesionalEntity findById(@PathVariable Long id) {
        return servicio.findById(id);
    }

    @PostMapping
    public NivelProfesionalEntity add(@RequestBody NivelProfesionalEntity obj) {
        return servicio.add(obj);
    }

    @PutMapping("/{id}")
    public NivelProfesionalEntity update(@RequestBody NivelProfesionalEntity obj, @PathVariable Long id) {
        return servicio.update(obj, id);
    }

    @DeleteMapping("/{id}")
    public NivelProfesionalEntity delete(@PathVariable Long id) {
        return servicio.delete(id);
    }

    @PutMapping("/enable/{id}")
    public NivelProfesionalEntity enable(@PathVariable Long id) {
        return servicio.enable(id);
    }
}
