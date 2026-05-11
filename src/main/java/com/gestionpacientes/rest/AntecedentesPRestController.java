package com.gestionpacientes.rest;

import com.gestionpacientes.dto.AntecedentesPatologicosDto;
import com.gestionpacientes.entity.AntecedentesPEntity;
import com.gestionpacientes.service.AntecedentesPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/antecedentespatoligicosRest")
public class AntecedentesPRestController {

    @Autowired
    private AntecedentesPService servicio;

    @GetMapping
    public List<AntecedentesPEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public AntecedentesPEntity findById(Long id) {
        return servicio.findById(id);
    }

    @GetMapping("/hc/{idHC}")
    public ResponseEntity<List<AntecedentesPatologicosDto>> buscarPatologiasPorCodigoHC(@PathVariable Long idHC) {
        List<AntecedentesPatologicosDto> dto = servicio.buscarPatologiasPorCodigoHC(idHC);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/{idHC}")
    public ResponseEntity<AntecedentesPatologicosDto> add(@RequestBody AntecedentesPatologicosDto obj,
            @PathVariable Long idHC) {
        AntecedentesPatologicosDto dto = servicio.add(obj, idHC);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{idHC}/{idPatologia}")
    public ResponseEntity<AntecedentesPatologicosDto> update(@RequestBody AntecedentesPatologicosDto obj,
            @PathVariable Long idHC, @PathVariable Long idPatologia) {
        AntecedentesPatologicosDto dto = servicio.update(obj, idHC, idPatologia);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicio.deleteById(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }

}
