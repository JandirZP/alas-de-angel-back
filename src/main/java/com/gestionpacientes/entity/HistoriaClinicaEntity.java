package com.gestionpacientes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity(name = "HistoriaClinicaEntity")
@Table(name = "HistoriaClinica")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistoriaClinicaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistoriaClinica", nullable = false)
    @EqualsAndHashCode.Include
    private Long codigo;

    // Relación 1 a 1 con Usuario (Paciente)
    @OneToOne
    @JoinColumn(name = "idUsuario", nullable = false, unique = true)
    private UsuarioEntity pacienteEntity;

    // Datos Biológicos Básicos
    @Column(name = "grupoSanguineo", nullable = false, length = 2)
    private String grupoSanguineo;

    @Column(name = "factorRH", nullable = false, length = 1)
    private String factorRH;

    // Antecedentes Hereditarios
    @Column(name = "antecedentesFamiliares", nullable = false)
    private Boolean antecedentesFamiliares;

    @Column(name = "especifiqueAnteFamil", columnDefinition = "TEXT")
    private String especifiqueAnteFamil;

    // Hábitos Legales
    @Column(name = "estadoAlcohol", nullable = false)
    private Integer estadoAlcohol; // 0:Nunca, 1:Ex, 2:Activo

    @Column(name = "frecuenciaAlcohol", length = 100)
    private String frecuenciaAlcohol;

    @Column(name = "estadoTabaco", nullable = false)
    private Integer estadoTabaco; // 0:Nunca, 1:Ex, 2:Activo

    @Column(name = "frecuenciaTabaco", length = 100)
    private String frecuenciaTabaco;

    @Column(name = "consumeDrogas", nullable = false)
    private Boolean consumeDrogas;

    // Sexualidad
    @Column(name = "sexualmenteActivo", nullable = false)
    private Boolean sexualmenteActivo;

    @Column(name = "edadInicioSexual")
    private Integer edadInicioSexual;

    @Column(name = "usaMetodoAnticonceptivo")
    private Boolean usaMetodoAnticonceptivo;

    @Column(name = "metodoPlanificacion", length = 100)
    private String metodoPlanificacion;

    // Historial Gineco-Obstétrico
    @Column(name = "tuvoEmbarazos")
    private Boolean tuvoEmbarazos;

    @Column(name = "cantidadGestaciones")
    private Integer cantidadGestaciones;

    @Column(name = "cantidadPartos")
    private Integer cantidadPartos;

    @Column(name = "cantidadAbortos")
    private Integer cantidadAbortos;

    @Column(name = "huboComplicacionesParto")
    private Boolean huboComplicacionesParto;

    @Column(name = "especifiqueComplicaciones", columnDefinition = "TEXT")
    private String especifiqueComplicaciones;

    @ManyToMany
    @JoinTable(name = "HistoriaClinicaEventos", joinColumns = @JoinColumn(name = "idHistoriaClinica"), inverseJoinColumns = @JoinColumn(name = "idEventoMedico"))
    @ToString.Exclude
    private Set<EventosMedicosEntity> eventosMedicos = new HashSet<>();

    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlergiasEntity> alergias;

    @OneToMany(mappedBy = "historiaClinicaEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AntecedentesPEntity> enfermedadeCronicas;

    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AntecedentesQEntity> cirugiasPrevias;

    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialDrogasEntity> drogas;

    // Auditoría
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
}
