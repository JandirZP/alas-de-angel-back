package com.gestionpacientes.service;

import com.gestionpacientes.entity.UbigeoEntity;

import java.util.List;

public interface UbigeoService {

    List<UbigeoEntity> findAll();
    UbigeoEntity findById(long idubigeo);
    UbigeoEntity add(UbigeoEntity obj);
    UbigeoEntity update(UbigeoEntity obj, long idUbigeo);

    List<String> listarDepartamentos();
    List<String> listarProvincias(String departamento);
    List<UbigeoEntity> listarDistritos(String departamento, String provincia);
}
