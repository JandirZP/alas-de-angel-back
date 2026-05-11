package com.gestionpacientes.repository;

import com.gestionpacientes.entity.EspecialidadEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Long> {

    @Query("SELECT e FROM EspecialidadEntity e WHERE e.estado = true")
    List<EspecialidadEntity> findAllCustom();

    List<EspecialidadEntity> findByMedicos_IdUsuario(Long idUsuario);
}
