package com.gestionpacientes.rest;

import com.gestionpacientes.dto.EspecialidadesDto;
import com.gestionpacientes.entity.EspecialidadEntity;
import com.gestionpacientes.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidad")
public class EspecialidadRestController {

    @Autowired
    private EspecialidadService servicio;

    @GetMapping
    public List<EspecialidadEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/custom")
    public List<EspecialidadesDto> findAllCustom() {
        return servicio.findAllCustom();
    }

    @GetMapping("/buscarPorMedico/{idUsuario}")
    public ResponseEntity<List<EspecialidadesDto>> buscarPorMedico(@PathVariable Long idUsuario) {
        List<EspecialidadesDto> lista = servicio.buscarPorMedico(idUsuario);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/{id}")
    public EspecialidadEntity findById(@PathVariable Long id) {
        return servicio.findById(id);
    }

    @PostMapping
    public EspecialidadEntity add(@RequestBody EspecialidadEntity obj) {
        return servicio.add(obj);
    }

    @PutMapping("/{id}")
    public EspecialidadEntity update(@RequestBody EspecialidadEntity obj, @PathVariable Long id) {
        return servicio.update(obj, id);
    }

    @DeleteMapping("/{id}")
    public EspecialidadEntity delete(@PathVariable Long id) {
        return servicio.delete(id);
    }

    @PutMapping("/enable/{id}")
    public EspecialidadEntity enable(@PathVariable Long id) {
        return servicio.enable(id);
    }

}
