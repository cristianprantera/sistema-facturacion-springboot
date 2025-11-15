package com.facturacion.sistema.models.entities;

import com.facturacion.sistema.models.enums.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter @Setter
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @Column(unique = true, nullable = false, length = 30)

	private String username;
    @Column(nullable = false)

	private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)

	private Rol rol;

}
