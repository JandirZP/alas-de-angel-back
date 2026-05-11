package com.gestionpacientes.rest;

import com.gestionpacientes.dto.AlergiasDto;
import com.gestionpacientes.entity.AlergiasEntity;
import com.gestionpacientes.service.AlergiasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alergiasRest")
public class AlergiasRestController {

    @Autowired
    private AlergiasService servicio;

    @GetMapping
    public List<AlergiasEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public AlergiasEntity findById(@PathVariable Long id) {
        return servicio.findById(id);
    }

    @GetMapping("/historia/{idHC}")
    public ResponseEntity<List<AlergiasDto>> findAlergiasByHistoria(@PathVariable Long idHC) {
        List<AlergiasDto> alergias = servicio.buscarAlergiasPorIdHistoria(idHC);
        return ResponseEntity.ok(alergias);
    }

    @PostMapping("/{idHC}")
    public ResponseEntity<AlergiasDto> add(@RequestBody AlergiasEntity obj, @PathVariable Long idHC) {
        AlergiasDto alergiasDto = servicio.add(obj, idHC);
        return ResponseEntity.status(HttpStatus.CREATED).body(alergiasDto);
    }

    @PutMapping("/{idHC}/{idAlergia}")
    public ResponseEntity<AlergiasDto> update(@RequestBody AlergiasEntity obj, @PathVariable Long idHC,
            @PathVariable Long idAlergia) {
        AlergiasDto alergiasDto = servicio.update(obj, idHC, idAlergia);
        return ResponseEntity.ok(alergiasDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
