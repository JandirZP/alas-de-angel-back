package com.gestionpacientes.rest;

import com.gestionpacientes.dto.AntecedentesQuirurgicosDto;
import com.gestionpacientes.entity.AntecedentesQEntity;
import com.gestionpacientes.service.AntecedentesQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/antecedentesquirurgicosRest")
public class AntecedentesQRestController {

    @Autowired
    private AntecedentesQService servicio;

    @GetMapping
    public List<AntecedentesQEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public AntecedentesQEntity findById(Long id) {
        return servicio.findById(id);
    }

    @GetMapping("/hc/{idHC}")
    public ResponseEntity<List<AntecedentesQuirurgicosDto>> buscarQuirurgicosPorCodigoHC(@PathVariable Long idHC) {
        List<AntecedentesQuirurgicosDto> dto = servicio.buscarQuirurgicosPorCodigoHC(idHC);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/{idHC}")
    public ResponseEntity<AntecedentesQuirurgicosDto> add(@RequestBody AntecedentesQuirurgicosDto obj,
            @PathVariable Long idHC) {
        AntecedentesQuirurgicosDto dto = servicio.add(obj, idHC);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{idHC}/{idOperacion}")
    public ResponseEntity<AntecedentesQuirurgicosDto> update(@RequestBody AntecedentesQuirurgicosDto obj,
            @PathVariable Long idHC, @PathVariable Long idOperacion) {
        AntecedentesQuirurgicosDto dto = servicio.update(obj, idHC, idOperacion);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicio.deleteById(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }

}
