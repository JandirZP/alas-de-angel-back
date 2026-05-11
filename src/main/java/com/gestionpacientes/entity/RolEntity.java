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

@Entity(name = "RolEntity")
@Table(name = "Rol")
public class RolEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol", nullable = false)
    private Long idRol;

    @Column(name = "nombre", length = 30, nullable = false)
    private String nombre;



    @Column(name = "estado", nullable = false)
    private Boolean estadoRol;
}
