package com.gestionpacientes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter // Reemplazamos @Data por Getter y Setter separados
@Setter
@Entity(name = "CitaEntity")
@Table(name = "Cita")
public class CitaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCita", nullable = false)
    private Long idCita;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fechaHora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "motivo", nullable = false, length = 200)
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "pacienteId", nullable = false)
    //ESTO ES LA MAGIA: Evita que Spring entre en bucle o muestre datos sensibles
    @JsonIgnoreProperties({"password", "roles", "hibernateLazyInitializer", "handler", "citas"})
    private UsuarioEntity paciente;

    @ManyToOne
    @JoinColumn(name = "medicoId", nullable = false)
    // Lo mismo para el médico. Solo queremos su nombre, foto, etc.
    @JsonIgnoreProperties({"password", "roles", "hibernateLazyInitializer", "handler", "citas"})
    private UsuarioEntity medico;

    @ManyToOne
    @JoinColumn(name = "idEspecialidad", nullable = false)
    private EspecialidadEntity especialidad;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
}