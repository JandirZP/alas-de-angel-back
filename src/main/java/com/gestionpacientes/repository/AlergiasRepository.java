package com.gestionpacientes.repository;

import com.gestionpacientes.entity.AlergiasEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlergiasRepository extends JpaRepository<AlergiasEntity, Long> {

    List<AlergiasEntity> findByHistoriaClinica_Codigo(Long idHC);

}
