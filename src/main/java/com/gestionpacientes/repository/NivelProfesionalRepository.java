package com.gestionpacientes.repository;

import com.gestionpacientes.entity.NivelProfesionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NivelProfesionalRepository extends JpaRepository<NivelProfesionalEntity, Long> {
    @Query("SELECT n FROM NivelProfesionalEntity n WHERE n.estado = true")
    List<NivelProfesionalEntity> findAllCustom();
}
