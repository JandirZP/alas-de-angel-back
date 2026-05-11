package com.gestionpacientes.service.impl;

import com.gestionpacientes.entity.UbigeoEntity;
import com.gestionpacientes.repository.UbigeoRepository;
import com.gestionpacientes.service.UbigeoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UbigeoServiceImpl implements UbigeoService {

    @Autowired
    private UbigeoRepository repositorio;

    @Override
    public List<UbigeoEntity> findAll() {
        return repositorio.findAll();
    }
    @Override
    public UbigeoEntity findById(long idubigeo) {
        return repositorio.findById(idubigeo)
                .orElseThrow(() -> new RuntimeException("El ubigeo no existe"));
    }
    @Override
    public UbigeoEntity add(UbigeoEntity obj){
        return repositorio.save(obj);
    }
    @Override
    public UbigeoEntity update(UbigeoEntity obj, long idUbigeo){
        UbigeoEntity objUbigeo = repositorio.findById(idUbigeo)
                .orElseThrow(() -> new RuntimeException("El ubigeo no existe"));
        BeanUtils.copyProperties(obj, objUbigeo, "idUbigeo");
        return repositorio.save(objUbigeo);
    }


    @Override
    @Transactional(readOnly = true)
    public List<String> listarDepartamentos() {
        return repositorio.findDepartamentos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> listarProvincias(String departamento) {
        return repositorio.findProvincias(departamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UbigeoEntity> listarDistritos(String departamento, String provincia) {
        return repositorio.findDistritos(departamento, provincia);
    }
}
