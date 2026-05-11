package com.gestionpacientes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "SedesEntity")
@Table(name = "Sedes")
public class SedesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSedes", nullable = false)
    private Long idSedes;

    @Column(name = "nombre", length = 60, nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUbigeo", nullable = false)
    private UbigeoEntity ubigeo;

    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    @Column(name = "referencia", length = 100)
    private String referencia;

    @Column(name = "telefonoContacto", length = 50, nullable = false)
    private String telefonoContacto;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
}
