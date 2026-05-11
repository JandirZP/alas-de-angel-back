package com.gestionpacientes.repository;

import com.gestionpacientes.entity.AntecedentesPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AntecedentesPRepository extends JpaRepository<AntecedentesPEntity, Long> {

    List<AntecedentesPEntity> findByHistoriaClinicaEntity_Codigo(Long idHC);

}
