package com.facturacion.sistema.models.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class ProductoResponseDTO {
	
	private String nombre;
	private BigDecimal precio;

}
