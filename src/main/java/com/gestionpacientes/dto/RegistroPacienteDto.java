package com.gestionpacientes.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RegistroPacienteDto {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fotoUrl;
    private LocalDate fechaNacimiento;
    private Long tipoDocumentoId;
    private String numeroDocumento;
    private Boolean sexo;
    private String celular;
    private String contactoEmergencia;
    private String celularContacto;
    private String direccion;
    private Long idUbigeo; 
    private String paisOrigen;
    private String nombreUsuario;
    private String correo;
    private String password;
}
