package com.gestionpacientes.service.impl;

import com.gestionpacientes.entity.RolEntity;
import com.gestionpacientes.repository.RolRepository;
import com.gestionpacientes.service.RolService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository repositorio;

    @Override
    public List<RolEntity> findAll(){
        return repositorio.findAll();
    }
    @Override
    public List<RolEntity> findAllCustom(){
        return repositorio.findAllCustom();
    }
    @Override
    public Optional<RolEntity> findByNombre(String nombre){
        return repositorio.findByNombre(nombre);
    }

    @Override
    public RolEntity findById(long id){
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El rol no existe"));
    }

    @Override
    public RolEntity add(RolEntity obj){
        return repositorio.save(obj);
    }
    @Override
    public RolEntity update(RolEntity obj, long id){
        RolEntity objRol = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El rol no existe"));
        BeanUtils.copyProperties(obj, objRol, "idRol");
        return repositorio.save(objRol);
    }
    @Override
    public RolEntity delete(long id){
        RolEntity objRol = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El rol no existe"));
        objRol.setEstadoRol(false);
        return repositorio.save(objRol);
    }
    @Override
    public RolEntity enable(long id){
        RolEntity objRol = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El rol no existe"));
        objRol.setEstadoRol(true);
        return repositorio.save(objRol);
    }
}
