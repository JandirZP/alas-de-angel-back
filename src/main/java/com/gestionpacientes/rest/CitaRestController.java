package com.gestionpacientes.rest;

import com.gestionpacientes.dto.CitaDto;
import com.gestionpacientes.entity.CitaEntity;
import com.gestionpacientes.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/citaRest")
public class CitaRestController {

    @Autowired
    private CitaService servicio;

    @GetMapping
    public List<CitaEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/todas")
    public ResponseEntity<List<CitaDto>> buscarTodasLasCitas() {
        List<CitaDto> citas = servicio.buscarTodasLasCitas();
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/custom")
    public List<CitaEntity> findAllCustom() {
        return servicio.findAllCustom();
    }

    @GetMapping("/porPaciente/{id}")
    public ResponseEntity<List<CitaDto>> listarPorPaciente(@PathVariable Long id) {
        List<CitaDto> citas = servicio.findByPacienteId(id);

        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/allCitasPorPaciente/{id}")
    public ResponseEntity<List<CitaDto>> listarAllCitasPorPaciente(@PathVariable Long id) {
        List<CitaDto> citas = servicio.findAllCitasPacienteId(id);

        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/paciente/documento/{numeroDocumento}")
    public ResponseEntity<List<CitaDto>> listarPacienteDocumento(@PathVariable String numeroDocumento) {
        List<CitaDto> citas = servicio.buscarCitasByDocumento(numeroDocumento);
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/fecha/{fechaInicio}/{fechaFin}")
    public ResponseEntity<List<CitaDto>> listarPorFecha(@PathVariable Date fechaInicio, @PathVariable Date fechaFin) {
        List<CitaDto> citas = servicio.buscarCitasPorFecha(fechaInicio, fechaFin);
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/ocupadas/{idMedico}/{fechaHora}")
    public ResponseEntity<List<CitaDto>> listarPorMedico(@PathVariable Long idMedico,
            @PathVariable @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd") java.time.LocalDate fechaHora) {
        java.time.LocalDateTime fechaInicio = fechaHora.atStartOfDay();
        List<CitaDto> citas = servicio.findByMedicoId(idMedico, fechaInicio);
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/porMedico/{id}")
    public ResponseEntity<List<CitaDto>> listarPorMedico(@PathVariable Long id) {
        List<CitaDto> citas = servicio.findAllActiveByMedicoId(id);

        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> buscarPorCodigo(@PathVariable Long id) {

        // 1. Llamamos a tu nuevo servicio que devuelve un Optional<CitaDto>
        Optional<CitaDto> citaEncontrada = servicio.buscarPorCodigo(id);

        // 2. Evaluamos si el Optional contiene algo o está vacío
        if (citaEncontrada.isPresent()) {
            // Si existe, devolvemos un estado 200 (OK) con el DTO adentro (.get() extrae el
            // valor)
            return ResponseEntity.ok(citaEncontrada.get());
        } else {
            // Si no existe, devolvemos un estado 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    // CAMBIO: Devuelve ResponseEntity<CitaDTO>
    public ResponseEntity<CitaDto> add(@RequestBody CitaEntity obj) {
        // El servicio ya nos devuelve el DTO limpio
        CitaDto citaCreada = servicio.add(obj);

        // Devolvemos 201 Created con el objeto seguro
        return ResponseEntity.status(HttpStatus.CREATED).body(citaCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDto> update(@RequestBody CitaEntity obj, @PathVariable Long id) {
        CitaDto citaActualizada = servicio.update(obj, id);
        return ResponseEntity.ok(citaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        String mensaje = servicio.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", mensaje);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Map<String, String>> enable(@PathVariable Long id) {
        String mensaje = servicio.enable(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", mensaje);
        return ResponseEntity.ok(response);
    }
}
