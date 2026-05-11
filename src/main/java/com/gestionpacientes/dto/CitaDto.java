package com.gestionpacientes.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CitaDto {
    private Long idCita;
    private LocalDateTime fechaHora;
    private String motivo;
    private Boolean estado;

    // Datos planos del médico y paciente
    private Long idMedico;
    private String nombreMedico;
    private String apellidoMedico;
    private String especialidadMedico;
    private Boolean sexoMedico;

    private Long idPaciente;
    private String nombrePaciente;
    private String apellidoPatPaciente;
    private String apellidoMatPaciente;
    private String tipoDocumento;
    private String numeroDocumento;
    private Boolean sexoPaciente;
    // Nuevo campo para saber si pasó por triaje
    private Boolean atendidoEnTriaje;
}
