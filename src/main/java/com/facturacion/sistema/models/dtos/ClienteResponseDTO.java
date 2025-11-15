package com.facturacion.sistema.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ClienteResponseDTO {
	private String nombreYApellido;
	private Long dni;
	private String email;
	private String nroCelular;
	private String tipoResponsabilidadFiscal;
}
