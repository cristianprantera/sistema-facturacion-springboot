package com.facturacion.sistema.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long expiresIn;
}
