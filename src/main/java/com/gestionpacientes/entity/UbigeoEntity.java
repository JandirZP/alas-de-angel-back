package com.gestionpacientes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity(name = "UbigeoEntity")
@Table(name = "Ubigeo")
public class UbigeoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idUbigeo", nullable = false)
    private Long idUbigeo;


    @Column(name = "departamento", length = 13, nullable = false)
    private String departamento;


    @Column(name = "provincia", length = 25, nullable = false)
    private String provincia;


    @Column(name = "distrito", length = 36, nullable = false)
    private String distrito;


    @Column(name = "nombCapitalLegal", length = 70)
    private String nombCapitalLegal;

    @Column(name = "codRegNat")
    private Integer codRegNat;


    @Column(name = "regionNatural", length = 10)
    private String regionNatural;


}
