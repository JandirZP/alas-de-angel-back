package com.gestionpacientes.repository;

import com.gestionpacientes.entity.EventosMedicosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventosMedicosRepository extends JpaRepository<EventosMedicosEntity, Long> {
    @Query("select ev from EventosMedicosEntity ev where ev.estado = true")
    List<EventosMedicosEntity> findAllCustom();


}
