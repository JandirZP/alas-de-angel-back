package com.gestionpacientes.service;

import com.gestionpacientes.entity.RolEntity;

import java.util.List;
import java.util.Optional;

public interface RolService {

    List<RolEntity> findAll();
    List<RolEntity> findAllCustom();
    Optional<RolEntity> findByNombre(String nombre);
    RolEntity findById(long id);
    RolEntity add(RolEntity obj);
    RolEntity update(RolEntity obj, long id);
    RolEntity delete(long id);
    RolEntity enable(long id);
}
