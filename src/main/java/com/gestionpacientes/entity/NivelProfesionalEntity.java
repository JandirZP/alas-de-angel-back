package com.gestionpacientes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity(name = "NivelProfesionalEntity")
@Table(name = "NivelProfesional")
public class NivelProfesionalEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNivProf", nullable = false)
    private Long idNivelProfesional;

    @Column(name = "nombNivel", length = 60, nullable = false)
    private String nombre;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
}
