package com.facturacion.sistema.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter

public abstract class Persona {
	protected String nombre;
	protected String apellido;	
	@Column(unique=true)
	protected Long dni;
	@Column(unique=true)
	protected String email;
	
	
	
}
