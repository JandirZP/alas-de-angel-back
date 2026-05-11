package com.gestionpacientes.service;

import com.gestionpacientes.entity.TipoDocumentoEntity;

import java.util.List;

public interface TipoDocumentoService {
    List<TipoDocumentoEntity> findAll();
    List<TipoDocumentoEntity> findActivos();
    List<TipoDocumentoEntity> findInactivos();
    TipoDocumentoEntity findById(Long id);
    TipoDocumentoEntity add(TipoDocumentoEntity obj);
    TipoDocumentoEntity update(TipoDocumentoEntity obj, Long id);
    TipoDocumentoEntity delete(Long id);
    TipoDocumentoEntity enable(Long id);



}
