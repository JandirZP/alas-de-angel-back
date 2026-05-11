package com.gestionpacientes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Entity(name = "EspecialidadEntity")
@Table(name = "Especialidad")
public class EspecialidadEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "idEspecialidad", nullable = false)
    private Long codigo;

    @Column(name = "nombre", length = 80, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 250, nullable = false)
    private String descripcion;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @JsonIgnoreProperties({"especialidades", "hibernateLazyInitializer", "handler"})
    @ManyToMany(mappedBy = "especialidades")
    private Set<UsuarioEntity> medicos = new HashSet<>();
}
