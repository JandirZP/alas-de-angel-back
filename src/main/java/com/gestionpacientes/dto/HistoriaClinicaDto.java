package com.gestionpacientes.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoriaClinicaDto {

    private Long idHC;
    private Long idPaciente;
    private String nombresPaciente;
    private String apellidoPaternoPaciente;
    private String apellidoMaternoPaciente;
    private String numeroDocumento;
    private String tipoDocumento;
    private String fechaNacimiento;
    private Boolean sexoPaciente;

    private String grupoSanquineo;
    private String factorRH;

    private Boolean antecedentesFamiliares;
    private String especifiqueAnteFamil;

    private Integer estadoAlcohol;
    private String frecuenciaAlcohol;

    private Integer estadoTabaco;
    private String frecuenciaTabaco;

    private Boolean consumeDrogas;

    private Boolean sexualmenteActivo;
    private Integer edadInicioSexual;
    private Boolean usaMetodoAnticonceptivo;
    private String metodoPlanificacion;

    private Boolean tuvoEmbarazos;
    private Integer cantidadGestaciones;
    private Integer cantidadPartos;
    private Integer cantidadAbortos;
    private Boolean huboComplicaciones;
    private String especifiqueComplicaciones;

    private LocalDateTime fechaCreacion;

    private Boolean estadoHC;

    private List<AlergiasDto> alergias;
    private List<AntecedentesPatologicosDto> antecedentesPatologicos;
    private List<AntecedentesQuirurgicosDto> antecedentesQuirurgicos;
    private List<HistorialDrogasDto> drogas;

    private List<EventosMedicosDto> eventosMedicos;

}
