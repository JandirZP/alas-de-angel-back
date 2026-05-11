package com.gestionpacientes.rest;

import com.gestionpacientes.entity.TipoDocumentoEntity;
import com.gestionpacientes.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipodocumento")
public class TipoDocumentoRest {

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @GetMapping
    public List<TipoDocumentoEntity> findAll(){
        return tipoDocumentoService.findAll();
    }

    @GetMapping("/activos")
    public List<TipoDocumentoEntity> findActivos(){
        return tipoDocumentoService.findActivos();
    }
    @GetMapping("/{id}")
    public TipoDocumentoEntity findById(@PathVariable Long id){
        return tipoDocumentoService.findById(id);
    }
    @PostMapping
    public TipoDocumentoEntity add(@RequestBody TipoDocumentoEntity obj){
        return tipoDocumentoService.add(obj);
    }
    @PutMapping("/{id}")
    public TipoDocumentoEntity update(@RequestBody TipoDocumentoEntity obj, @PathVariable Long id){
        return tipoDocumentoService.update(obj, id);
    }
    @DeleteMapping("/{id}")
    public TipoDocumentoEntity delete(@PathVariable Long id){
        return tipoDocumentoService.delete(id);
    }
    @PutMapping("/enable/{id}")
    public TipoDocumentoEntity enable(@PathVariable Long id){
        return tipoDocumentoService.enable(id);
    }
}
