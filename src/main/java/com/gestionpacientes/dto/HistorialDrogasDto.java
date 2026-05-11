package com.gestionpacientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialDrogasDto {
    private Long id;
    private Long historiaClinicaId;
    private String nombreDroga;
    private String frecuencia;
    private String observaciones;
}
