package com.gestionpacientes.repository;

import com.gestionpacientes.entity.HistorialDrogasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialDrogasRepository extends JpaRepository<HistorialDrogasEntity, Long> {

    List<HistorialDrogasEntity> findByHistoriaClinica_Codigo(Long idHC);

}
