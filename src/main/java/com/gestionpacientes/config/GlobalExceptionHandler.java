package com.gestionpacientes.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Captura CUALQUIER excepción no manejada y la registra completamente en logs.
     * Así Render mostrará la causa exacta del error 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {

        // Esto aparecerá en los logs de Render con el stack trace completo
        log.error("========== ERROR NO CONTROLADO ==========");
        log.error("Tipo: {}", ex.getClass().getName());
        log.error("Mensaje: {}", ex.getMessage());
        log.error("Stack trace completo:", ex);
        log.error("=========================================");

        // Respuesta JSON con info del error (útil para el navegador también)
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", 500);
        body.put("error", "Internal Server Error");
        body.put("causa", ex.getClass().getSimpleName());
        body.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
