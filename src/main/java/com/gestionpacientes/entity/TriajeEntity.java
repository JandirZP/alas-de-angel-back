package com.gestionpacientes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity(name = "TriajeEntity")
@Table(name = "Triaje")
public class TriajeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTriaje", nullable = false)
    private Long idTriaje;

    @OneToOne
    @JoinColumn(name = "idCita", nullable = false, unique = true)
    @JsonIgnoreProperties({ "password", "roles", "hibernateLazyInitializer", "handler", "citas", "triaje" })
    private CitaEntity cita;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonIgnoreProperties({ "password", "roles", "hibernateLazyInitializer", "handler", "citas", "triaje" })
    private UsuarioEntity enfermera;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fechaHora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "peso", nullable = false, precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "altura", nullable = false, precision = 3, scale = 2)
    private BigDecimal altura;

    @Column(name = "presionArterial", nullable = false, length = 20)
    private String presionArterial;

    @Column(name = "temperatura", nullable = false, precision = 4, scale = 1)
    private BigDecimal temperatura;

    @Column(name = "tieneFiebre", nullable = false)
    private Boolean tieneFiebre;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fechaUltimaRegla")
    private LocalDate fechaUltimaRegla;

    @Column(name = "estaEmbarazada")
    private Boolean estaEmbarazada;

    @Column(name = "semanasGestacion")
    private Integer semanasGestacion;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
}
