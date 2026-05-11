package com.gestionpacientes.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioProfileDto {

    // Datos Personales
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
}
