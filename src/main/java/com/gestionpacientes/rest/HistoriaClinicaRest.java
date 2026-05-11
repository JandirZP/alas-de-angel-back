package com.gestionpacientes.rest;

import com.gestionpacientes.dto.HistoriaClinicaDto;
import com.gestionpacientes.dto.TratamientoDto;
import com.gestionpacientes.entity.HistoriaClinicaEntity;
import com.gestionpacientes.service.HistoriaClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historiaclinicaRest")
public class HistoriaClinicaRest {

    @Autowired
    private HistoriaClinicaService servicio;

    @GetMapping
    public List<HistoriaClinicaEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/custom")
    public ResponseEntity<List<HistoriaClinicaDto>> findAllActivas() {
        List<HistoriaClinicaDto> historias = servicio.findActivas();
        if (historias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historias);
    }

    @GetMapping("/{id}")
    public HistoriaClinicaEntity findById(@PathVariable Long id) {
        return servicio.findById(id);
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<HistoriaClinicaDto> findDtoCompletoById(@PathVariable Long id) {
        return servicio.findDtoCompletoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/ultimo-tratamiento/{idPaciente}")
    public ResponseEntity<TratamientoDto> getUltimoTratamiento(@PathVariable Long idPaciente) {
        return servicio.obtenerUltimoTratamiento(idPaciente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/documento/{numeroDocumento}")
    public ResponseEntity<HistoriaClinicaDto> getHistoriaPorDocumento(@PathVariable String numeroDocumento) {
        return servicio.buscarPorDocumento(numeroDocumento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<HistoriaClinicaDto> findHistoriaPorIdPaciente(@PathVariable Long idPaciente) {
        return servicio.buscarPorIdPaciente(idPaciente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<HistoriaClinicaDto> add(@RequestBody HistoriaClinicaEntity obj) {
        HistoriaClinicaDto historiaClinicaDto = servicio.add(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(historiaClinicaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinicaDto> update(@RequestBody HistoriaClinicaEntity obj, @PathVariable Long id) {
        HistoriaClinicaDto historiaClinicaDto = servicio.update(obj, id);
        return ResponseEntity.ok(historiaClinicaDto);
    }

    @DeleteMapping("/{id}")
    public HistoriaClinicaEntity delete(@PathVariable Long id) {
        return servicio.delete(id);
    }

    @PutMapping("/enable/{id}")
    public HistoriaClinicaEntity enable(@PathVariable Long id) {
        return servicio.enable(id);
    }
}
