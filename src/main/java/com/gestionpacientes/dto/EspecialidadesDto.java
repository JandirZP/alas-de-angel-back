package com.gestionpacientes.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadesDto {
    private Long codigo;
    private String nombre;
    private String descripcion;
    private Boolean estado;
}
