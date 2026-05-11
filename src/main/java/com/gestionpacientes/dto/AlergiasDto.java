package com.gestionpacientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlergiasDto {
    private Long id;
    private Long historiaClinicaId;
    private String alergeno;
    private String reaccion;
    private String observaciones;
}
