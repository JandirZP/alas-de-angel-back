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

@Entity(name = "TipoDocumentoEntity")
@Table(name = "TipoDocumento")
public class TipoDocumentoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipDoc")
    private Long idTipoDoc;

    @Column(name = "nombTipoDoc", nullable = false, length = 60)
    private String nombre;


    @Column(name = "estado", nullable = false)
    private Boolean estado;
}
