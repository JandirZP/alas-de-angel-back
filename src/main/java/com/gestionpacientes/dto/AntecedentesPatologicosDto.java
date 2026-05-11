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

public class AntecedentesPatologicosDto {
    private Long id;
    private Long historiaClinicaId;
    private String nombre;
    private LocalDate fechaDiagnostico;
    private boolean enTratamiento;
    private String observaciones;
}
