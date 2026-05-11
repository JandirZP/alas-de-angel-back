package com.gestionpacientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NivelProfesionalDto {
    private Long idNivelProfesionalDto;
    private String nombreDto;
    private Boolean estadoDto;
}
