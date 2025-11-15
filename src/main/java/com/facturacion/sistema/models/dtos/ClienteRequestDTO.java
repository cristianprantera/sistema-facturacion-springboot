package com.facturacion.sistema.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ClienteRequestDTO {
	
	@NotBlank(message="El nombre no puede estar vacío")
    @Size(max = 30, message = "El nombre no puede tener más de 30 caracteres")
	private String nombre;
	
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 30, message = "El apellido no puede tener más de 30 caracteres")
	private String apellido;
    
    @NotNull(message = "El DNI no puede ser nulo")
    @Min(value = 1000000, message = "El DNI debe ser mayor a 1.000.000")
    @Max(value = 99999999, message = "El DNI no puede tener más de 8 dígitos")
	private Long dni;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
	private String email;
    
    @NotBlank(message = "El número de celular es obligatorio")
    @Pattern(
    	    regexp = "^11\\d{8}$",
    	    message = "El número debe comenzar con 11 y tener 10 dígitos en total"
    	)
	private String nroCelular;
    
    @NotBlank(message = "La responsabilidad fiscal es obligatoria")
    @Pattern(
        regexp = "RESPONSABLE_INSCRIPTO|MONOTRIBUTISTA|CONSUMIDOR_FINAL|EXENTO",
        message = "Los valores válidos son: RESPONSABLE_INSCRIPTO, MONOTRIBUTISTA, CONSUMIDOR_FINAL, EXENTO"
    )
    private String tipoResponsabilidadFiscal;


}
