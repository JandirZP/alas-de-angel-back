package com.gestionpacientes.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.gestionpacientes.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file, String folderName){
        try {
            // "folder" es para organizar las fotos en carpetas dentro de Cloudinary
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", folderName
            ));
        } catch (IOException e){
            throw new RuntimeException("Error al subir la imagen a Cloudinary", e);
        }
    }

}
