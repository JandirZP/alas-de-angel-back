package com.gestionpacientes.repository;

import com.gestionpacientes.entity.CitaEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CitaRepository extends JpaRepository<CitaEntity, Long> {
        @Query("select c from CitaEntity c where c.estado = true")
        List<CitaEntity> findAllCustom();

        // Buscar todas las citas de hoy
        @Query("select c from CitaEntity c where c.fechaHora between :fechaInicio and :fechaFin")
        List<CitaEntity> findCitasByFechaHoraBetween(@Param("fechaInicio") Date fechaInicio,
                        @Param("fechaFin") Date fechaFin);

        List<CitaEntity> findByPaciente_IdUsuarioAndEstadoTrueOrderByFechaHoraAsc(Long idPaciente);

        List<CitaEntity> findByMedico_IdUsuarioAndEstadoTrueOrderByFechaHoraAsc(Long idMedico);

        // Buscar Cita por numero de documento de paciente
        @Query("select c from CitaEntity c where c.paciente.numeroDocumento = :documento")
        List<CitaEntity> findCitasByDoc(@Param("documento") String documento);

        @Query("SELECT c FROM CitaEntity c WHERE c.medico.idUsuario = :idMedico AND c.estado = true AND c.fechaHora >= :fechaInicio AND c.fechaHora < :fechaFin ORDER BY c.fechaHora ASC")
        List<CitaEntity> findCitasPorMedicoYRangoFecha(@Param("idMedico") Long idMedico,
                        @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

        boolean existsByMedico_IdUsuarioAndFechaHoraAndEstadoTrue(Long idMedico, LocalDateTime fechaHora);

        // Encontrar todas las citas de un paciente de forma ascendente
        List<CitaEntity> findByPaciente_IdUsuarioOrderByFechaHoraAsc(Long idPaciente);

}
