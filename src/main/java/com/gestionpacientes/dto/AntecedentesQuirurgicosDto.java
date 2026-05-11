package com.gestionpacientes.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AntecedentesQuirurgicosDto {
    private Long id;
    private Long historiaClinicaId;
    private String nombre;
    private LocalDate fecha;
    private boolean huboComplicaciones;
    private String observaciones;
}
