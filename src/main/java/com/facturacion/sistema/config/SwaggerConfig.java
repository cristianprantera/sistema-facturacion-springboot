package com.facturacion.sistema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {

        // 🔐 Configuración del esquema de seguridad Bearer JWT
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        // 🔐 Se añade el requisito de seguridad a los endpoints
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("BearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Facturación API")
                        .version("v1.0.0")
                        .description("API REST para gestión de clientes, productos y facturación.")
                        .contact(new Contact()
                                .name("Cristian Prantera")
                                .url("https://www.linkedin.com/in/cristianprantera")))
                
                // Se registra "BearerAuth"
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", bearerScheme))
                
                // Se exige BearerAuth por defecto
                .addSecurityItem(securityRequirement);
    }
}
