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

@Entity(name = "AntecedentesQEntity")
@Table(name = "AntecedentesQuirurgicos")
public class AntecedentesQEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOperacion", nullable = false)
    private Long idOperacion;

    @ManyToOne
    @JoinColumn(name = "idHistoriaClinica", nullable = false)
    private HistoriaClinicaEntity historiaClinica;

    @Column(name = "nombreOperacion", nullable = false, length = 150)
    private String nombreOperacion;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fechaOperacion", nullable = true)
    private LocalDate fechaOperacion;

    @Column(name = "huboComplicaciones", nullable = false)
    private Boolean huboComplicaciones;

    @Column(name = "observaciones", columnDefinition = "TEXT", nullable = true)
    private String observaciones;
}
