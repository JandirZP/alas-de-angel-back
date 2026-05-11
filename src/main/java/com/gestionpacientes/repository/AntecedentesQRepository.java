package com.gestionpacientes.repository;

import com.gestionpacientes.entity.AntecedentesQEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AntecedentesQRepository extends JpaRepository<AntecedentesQEntity, Long> {

    List<AntecedentesQEntity> findByHistoriaClinica_Codigo(Long idHC);

}
