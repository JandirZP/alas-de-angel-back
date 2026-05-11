package com.gestionpacientes.rest;

import com.gestionpacientes.entity.RolEntity;
import com.gestionpacientes.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rolRest")
public class RolRestController {

    @Autowired
    private RolService servicio;

    @GetMapping
    public List<RolEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/custom")
    public List<RolEntity> findAllCustom() {
        return servicio.findAllCustom();
    }

    @GetMapping("/{id}")
    public RolEntity findById(@PathVariable long id) {
        return servicio.findById(id);
    }

    @PostMapping
    public RolEntity add(@RequestBody RolEntity obj) {
        return servicio.add(obj);
    }

    @PutMapping("/{id}")
    public RolEntity update(@RequestBody RolEntity obj, @PathVariable long id) {
        return servicio.update(obj, id);
    }

    @DeleteMapping("/{id}")
    public RolEntity delete(@PathVariable long id) {
        return servicio.delete(id);
    }

    @PutMapping("/enable/{id}")
    public RolEntity enable(@PathVariable long id) {
        return servicio.enable(id);
    }
}
