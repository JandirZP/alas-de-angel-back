package com.gestionpacientes.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TriajeDto {

    private Long idTriaje;

    // RELACIONES (Solo los IDs para Entrada, y Nombres para Salida)
    private Long idCita;
    private Long idEnfermera;
    private String nombreEnfermera;
    private String apellidoEnfermera;

    private Long idPaciente;
    private String nombrePaciente;
    private String apellidoPaciente;
    private Boolean sexoPaciente;

    // DATOS MÉDICOS (Igual a tu Entity)
    private LocalDateTime fechaHora;
    private BigDecimal peso;
    private BigDecimal altura;
    private String presionArterial;
    private BigDecimal temperatura;
    private Boolean tieneFiebre;

    // DATOS GINECOLÓGICOS
    private LocalDate fechaUltimaRegla;
    private Boolean estaEmbarazada;
    private Integer semanasGestacion;

    private Boolean estado;
}
