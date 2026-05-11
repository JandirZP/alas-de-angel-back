package com.gestionpacientes.repository;

import com.gestionpacientes.entity.UbigeoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbigeoRepository extends JpaRepository<UbigeoEntity, Long> {

    // 1. Obtener lista única de Departamentos (ordenados alfabéticamente)
    @Query("SELECT DISTINCT u.departamento FROM UbigeoEntity u ORDER BY u.departamento ASC")
    List<String> findDepartamentos();

    // 2. Obtener lista única de Provincias filtrando por Departamento
    @Query("SELECT DISTINCT u.provincia FROM UbigeoEntity u WHERE u.departamento = :departamento ORDER BY u.provincia ASC")
    List<String> findProvincias(@Param("departamento") String departamento);

    // 3. Obtener lista de Distritos (Entidades completas) filtrando por Dep y Prov
    // Aquí necesitamos la entidad completa para poder obtener el 'idUbigeo' final
    @Query("SELECT u FROM UbigeoEntity u WHERE u.departamento = :departamento AND u.provincia = :provincia ORDER BY u.distrito ASC")
    List<UbigeoEntity> findDistritos(@Param("departamento") String departamento, @Param("provincia") String provincia);


}
