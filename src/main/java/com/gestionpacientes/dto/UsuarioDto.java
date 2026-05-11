package com.gestionpacientes.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDto {

    private Long idUsuario;
    private String nombres;
    private String fotoUrl;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String numeroDocumento;
    private Boolean sexo;
    private String paisOrigen;

    // Contacto
    private String correo;
    private String celular;
    private String contactoEmergencia;
    private String celularContacto;

    // Dirección y Ubigeo
    private String direccion; // Texto libre (Av. Siempre Viva)
    private Long idUbigeo; // Solo necesitamos el ID del distrito seleccionado
    private Boolean tieneHistoriaClinica;

    private List<EspecialidadesDto> especialidades;

}
