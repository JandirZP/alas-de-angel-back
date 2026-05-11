package com.gestionpacientes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventosMedicosDto {

    private Long idEventoMedico;
    private String tipoEvento;

    private String descripcion;
    private String diagnostico;
    private String medicamentos;
    private String dieta;
    private String recomendaciones;
    private LocalDateTime fechaHora;

    private Long idTriaje;
    private BigDecimal peso;
    private BigDecimal altura;
    private String presionArterial;
    private BigDecimal temperatura;
    private Boolean tieneFiebre;

    // DATOS GINECOLÓGICOS
    private LocalDate fechaUltimaRegla;
    private Boolean estaEmbarazada;
    private Integer semanasGestacion;
    private String nombreDoctor;
    private String apellidoDoctor;
    private Boolean estado;

}
