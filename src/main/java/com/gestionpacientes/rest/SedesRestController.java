package com.gestionpacientes.rest;

import com.gestionpacientes.dto.SedesDto;
import com.gestionpacientes.service.SedesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sedes")
public class SedesRestController {
    
    @Autowired
    private SedesService sedesService;

    @GetMapping("/activos")
    public ResponseEntity<List<SedesDto>> listarActivos() {
        return ResponseEntity.ok(sedesService.findAllActivos());
    }
}
