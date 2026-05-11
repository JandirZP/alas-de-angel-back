package com.gestionpacientes.repository;

import com.gestionpacientes.entity.HistoriaClinicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinicaEntity, Long> {
    @Query("select h from HistoriaClinicaEntity h where h.estado = true")
    List<HistoriaClinicaEntity> findAllCustom();

    Optional<HistoriaClinicaEntity> findByPacienteEntity_IdUsuario(Long idUsuario);

    boolean existsByPacienteEntity_IdUsuario(Long idUsuario);

    Optional<HistoriaClinicaEntity> findByPacienteEntity_NumeroDocumento(String numeroDocumento);

}
