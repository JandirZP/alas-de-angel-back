package com.gestionpacientes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter // Adiós @Data
@Setter
@Builder

@Entity(name = "EventosMedicosEntity")
@Table(name = "EventosMedicos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EventosMedicosEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEventoMedico", nullable = false)
    @EqualsAndHashCode.Include
    private Long idEventoMedico;

    @Column(name = "tipoEvento", nullable = false, length = 50)
    private String tipoEvento;

    @ManyToOne
    @JoinColumn(name = "idTriaje", nullable = false)
    private TriajeEntity triaje;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "medicamentos", columnDefinition = "TEXT")
    private String medicamentos;

    @Column(name = "dieta", columnDefinition = "TEXT")
    private String dieta;

    @Column(name = "recomendaciones", columnDefinition = "TEXT")
    private String recomendaciones;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "fechaHora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    // Relación Many-to-Many con HistoriaClinica
    @ManyToMany(mappedBy = "eventosMedicos")
    @ToString.Exclude
    private Set<HistoriaClinicaEntity> historiasClinicas = new HashSet<>();
}
