package com.gestionpacientes.repository;

import com.gestionpacientes.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {
    @Query("SELECT r FROM RolEntity r WHERE r.estadoRol = true")
    List<RolEntity> findAllCustom();

    Optional<RolEntity> findByNombre(String nombre);


}
