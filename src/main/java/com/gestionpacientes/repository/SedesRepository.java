package com.gestionpacientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestionpacientes.entity.SedesEntity;

import java.util.List;

@Repository
public interface SedesRepository extends JpaRepository<SedesEntity, Long> {
    List<SedesEntity> findByEstado(Boolean estado);
}
