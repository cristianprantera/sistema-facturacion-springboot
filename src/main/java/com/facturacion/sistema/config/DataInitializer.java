package com.facturacion.sistema.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.facturacion.sistema.models.entities.Usuario;
import com.facturacion.sistema.models.enums.Rol;
import com.facturacion.sistema.repositories.IUsuarioRepository;



@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner init(
            IUsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {

            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setUsername("admin12345");
                admin.setPassword(passwordEncoder.encode("admin12345"));
                admin.setRol(Rol.ADMIN);

                usuarioRepository.save(admin);

                System.out.println("### Usuario ADMIN creado ###");
                System.out.println("username: admin12345 | password: admin12345");
            }
        };
    }
}
