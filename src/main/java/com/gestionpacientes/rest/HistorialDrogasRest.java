package com.gestionpacientes.rest;

import com.gestionpacientes.dto.HistorialDrogasDto;
import com.gestionpacientes.entity.HistorialDrogasEntity;
import com.gestionpacientes.service.HistorialDrogasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historialdrogasRest")
public class HistorialDrogasRest {

    @Autowired
    private HistorialDrogasService servicio;

    @GetMapping
    public List<HistorialDrogasEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public HistorialDrogasEntity findById(@PathVariable Long id) {
        return servicio.findById(id);
    }

    @GetMapping("/hc/{idHC}")
    public ResponseEntity<List<HistorialDrogasDto>> buscarDrogasPorCodigoHC(@PathVariable Long idHC) {
        List<HistorialDrogasDto> dto = servicio.buscarDrogasPorCodigoHC(idHC);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/{idHC}")
    public ResponseEntity<HistorialDrogasDto> add(@RequestBody HistorialDrogasEntity obj, @PathVariable Long idHC) {
        HistorialDrogasDto dto = servicio.add(obj, idHC);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{idHC}/{idDrogas}")
    public ResponseEntity<HistorialDrogasDto> update(@RequestBody HistorialDrogasEntity obj, @PathVariable Long idHC,
            @PathVariable Long idDrogas) {
        HistorialDrogasDto dto = servicio.update(obj, idHC, idDrogas);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
