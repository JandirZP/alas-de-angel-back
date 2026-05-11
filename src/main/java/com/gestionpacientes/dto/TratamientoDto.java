package com.gestionpacientes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TratamientoDto {
    private Long idEventoMedico;
    private String medicamentos;
    private String dieta;
    private String recomendaciones;
    private LocalDateTime fecha;
    private String nombreMedico;
    private String apellidoMedico;


}
