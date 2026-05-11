package com.gestionpacientes.repository;

import com.gestionpacientes.entity.TipoDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoEntity, Long> {
    @Query("select tip from TipoDocumentoEntity tip where tip.estado=true")
    List<TipoDocumentoEntity> findActivosTodos();

    @Query("select tip from TipoDocumentoEntity tip where tip.estado=false")
    List<TipoDocumentoEntity> findInactivosTodos();

}
