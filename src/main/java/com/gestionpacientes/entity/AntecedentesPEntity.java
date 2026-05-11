package com.gestionpacientes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity(name = "AntecedentesPEntity")
@Table(name = "AntecedentesPatologicos")
public class AntecedentesPEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPatologia", nullable = false)
    private Long idPatologia;

    @ManyToOne
    @JoinColumn(name = "idHistoriaClinica", nullable = false)
    private HistoriaClinicaEntity historiaClinicaEntity;

    @Column(name = "nombreEnfermedad", nullable = false, length = 100)
    private String nombreEnfermedad;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fechaDiagnostico", nullable = true)
    private LocalDate fechaDiagnostico;

    @Column(name = "estaEnTratamiento", nullable = false)
    private Boolean estaEnTratamiento;

    @Column(name = "observaciones", columnDefinition = "TEXT", nullable = true)
    private String observaciones;
}
