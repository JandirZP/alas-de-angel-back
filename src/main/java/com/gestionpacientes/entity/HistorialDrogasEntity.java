package com.gestionpacientes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity(name = "HistorialDrogasEntity")
@Table(name = "HistorialDrogas")
public class HistorialDrogasEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDrogas", nullable = false)
    private Long idDrogas;

    @ManyToOne
    @JoinColumn(name = "idHistoriaClinica", nullable = false)
    private HistoriaClinicaEntity historiaClinica;

    @Column(name = "nombreDroga", nullable = false, length = 100)
    private String nombreDroga;

    @Column(name = "frecuencia", nullable = false, length = 100)
    private String frecuencia;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}
