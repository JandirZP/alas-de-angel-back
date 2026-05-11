package com.gestionpacientes.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDto {
    private String token;
    private String nombUsuario;
    private List<String> roles;

    //Constructor simple para facilitar la vida

    public LoginResponseDto(String token, String nombUsuario, List<String> roles) {
        this.token = token;
        this.nombUsuario = nombUsuario;
        this.roles = roles;
    }
}
