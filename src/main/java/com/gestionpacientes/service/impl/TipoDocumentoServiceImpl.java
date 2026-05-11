package com.gestionpacientes.service.impl;

import com.gestionpacientes.entity.TipoDocumentoEntity;
import com.gestionpacientes.repository.TipoDocumentoRepository;
import com.gestionpacientes.service.TipoDocumentoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Override
    public List<TipoDocumentoEntity> findAll(){
        return tipoDocumentoRepository.findAll();
    }

    @Override
    public List<TipoDocumentoEntity> findActivos(){

        return tipoDocumentoRepository.findActivosTodos();
    }

    @Override
    public List<TipoDocumentoEntity> findInactivos(){

        return tipoDocumentoRepository.findInactivosTodos();
    }

    @Override
    public TipoDocumentoEntity findById(Long id){
        return tipoDocumentoRepository.findById(id).orElse(null);
    }

    @Override
    public TipoDocumentoEntity add(TipoDocumentoEntity obj){
        return tipoDocumentoRepository.save(obj);
    }

    @Override
    public TipoDocumentoEntity update(TipoDocumentoEntity obj, Long id){
        TipoDocumentoEntity objUpdate = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El tipo de documento no existe"));

        BeanUtils.copyProperties(obj, objUpdate, "idTipoDoc");

        return tipoDocumentoRepository.save(objUpdate);
    }

    @Override
    public TipoDocumentoEntity delete(Long id){
        TipoDocumentoEntity obj = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El tipo de documento no existe"));
        obj.setEstado(false);
        return tipoDocumentoRepository.save(obj);
    }
    @Override
    public TipoDocumentoEntity enable(Long id){
        TipoDocumentoEntity obj = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El tipo de documento no existe"));
        obj.setEstado(true);
        return tipoDocumentoRepository.save(obj);
    }
}
