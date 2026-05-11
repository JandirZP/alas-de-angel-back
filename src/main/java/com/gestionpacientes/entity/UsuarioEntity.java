package com.gestionpacientes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity(name = "UsuarioEntity")
@Table(name = "Usuario")
public class UsuarioEntity implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idUsuario", nullable = false)
        public Long idUsuario;

        @Column(name = "nombres", nullable = false, length = 100)
        public String nombres;

        @Column(name = "apelliPat", nullable = false, length = 50)
        public String apellidoPaterno;

        @Column(name = "apelliMat", nullable = false, length = 50)
        public String apellidoMaterno;

        @Column(name = "fotoUrl", length = 500)
        private String fotoUrl;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @Column(name = "fechaNac", nullable = false)
        public LocalDate fechaNacimiento;

        @ManyToOne
        @JoinColumn(name = "idTipDoc", nullable = false)
        public TipoDocumentoEntity tipoDocumentoEntity;

        @Column(name = "nroDoc", nullable = false, length = 15)
        public String numeroDocumento;

        @Column(name = "sexo", nullable = false)
        public Boolean sexo;

        @Column(name = "celular", nullable = false, length = 20)
        public String celular;

        @Column(name = "contactoEmergencia", nullable = false, length = 80)
        public String contactoEmergencia;

        @Column(name = "celularContacto", nullable = false, length = 20)
        public String celularContacto;

        @Column(name = "direccion", nullable = false, length = 100)
        private String direccion;

        @ManyToOne
        @JoinColumn(name = "idUbigeo", nullable = false)
        private UbigeoEntity ubigeoEntity;

        @Column(name = "paisOrigen", nullable = false, length = 30)
        private String paisOrigen;

        @Column(name = "nombUsuario", nullable = false, length = 50)
        private String nombreUsuario;

        @Column(name = "correo", nullable = false, length = 80)
        private String correo;

        @Column(name = "contrasenha", nullable = false, length = 100)
        private String password;

        @ManyToOne
        @JoinColumn(name = "idNivProf")
        private NivelProfesionalEntity nivelProfesionalEntity;

        @Column(name = "estado", nullable = false)
        private Boolean estado;

        @JsonIgnoreProperties({ "usuarios", "hibernateLazyInitializer", "handler" })
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "UserXRol", joinColumns = @JoinColumn(name = "usuarioId"), inverseJoinColumns = @JoinColumn(name = "rolId"))

        private Set<RolEntity> roles = new HashSet<>();

        @JsonIgnoreProperties({ "medicos", "hibernateLazyInitializer", "handler" })
        @ManyToMany
        @JoinTable(name = "Medico_Especialidad", joinColumns = @JoinColumn(name = "idUsuario"), inverseJoinColumns = @JoinColumn(name = "idEspecialidad"))

        private Set<EspecialidadEntity> especialidades = new HashSet<>();

}
