package com.gestionpacientes.repository;

import com.gestionpacientes.entity.TriajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TriajeRepository extends JpaRepository<TriajeEntity, Long> {
    @Query("select t from TriajeEntity t where t.estado = true")
    List<TriajeEntity> findAllCustom();

    // Saber si una cita ya cuenta con un triaje (es decir, ya fue atendido)
    boolean existsByCita_IdCita(Long idCita);

    // Obtener triaje por cita
    Optional<TriajeEntity> findByCita_IdCita(Long idCita);
}
