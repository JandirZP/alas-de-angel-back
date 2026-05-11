package com.gestionpacientes.rest;

import com.gestionpacientes.entity.UbigeoEntity;
import com.gestionpacientes.service.UbigeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ubigeoRest")
public class UbigeoRestController {

    @Autowired
    private UbigeoService servicio;

    @GetMapping
    public List<UbigeoEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public UbigeoEntity findById(@PathVariable long id) {
        return servicio.findById(id);
    }

    @PostMapping
    public UbigeoEntity add(@RequestBody UbigeoEntity obj) {
        return servicio.add(obj);
    }

    @PutMapping("/{id}")
    public UbigeoEntity update(@RequestBody UbigeoEntity obj, @PathVariable long id) {
        return servicio.update(obj, id);
    }


    @GetMapping("/departamentos")
    public ResponseEntity<List<String>> listarDepartamentos() {
        return ResponseEntity.ok(servicio.listarDepartamentos());
    }

    @GetMapping("/provincias/{departamento}")
    public ResponseEntity<List<String>> listarProvincias(@PathVariable String departamento) {
        return ResponseEntity.ok(servicio.listarProvincias(departamento));
    }

    @GetMapping("/distritos/{departamento}/{provincia}")
    public ResponseEntity<List<UbigeoEntity>> listarDistritos(
            @PathVariable String departamento,
            @PathVariable String provincia) {
        return ResponseEntity.ok(servicio.listarDistritos(departamento, provincia));
    }
}
