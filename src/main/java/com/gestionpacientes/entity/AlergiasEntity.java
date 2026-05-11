package com.gestionpacientes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity(name = "AlergiasEntity")
@Table(name = "Alergias")
public class AlergiasEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlergia", nullable = false)
    private Long idAlergia;

    @ManyToOne
    @JoinColumn(name = "idHistoriaClinica", nullable = false)
    private HistoriaClinicaEntity historiaClinica;

    @Column(name = "alergeno", nullable = false, length = 100)
    private String alergeno;

    @Column(name = "reaccion", length = 100, nullable = true)
    private String reaccion;

    @Column(name = "observaciones", columnDefinition = "TEXT", nullable = true)
    private String observaciones;
}
