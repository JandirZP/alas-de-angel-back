package com.gestionpacientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SedesDto {
    private Long idSedes;
    private String nombre;
    private String direccion;
    private String referencia;
    private String telefonoContacto;
    private Boolean estado;
    
    // Datos de Ubigeo
    private Long idUbigeo;
    private String departamento;
    private String provincia;
    private String distrito;
}
