package com.facturacion.sistema.models.entities;

import com.facturacion.sistema.models.enums.TipoResponsabilidadFiscal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Cliente extends Persona {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nroCelular;
	
    @Enumerated(EnumType.STRING)
    private TipoResponsabilidadFiscal tipoResponsabilidadFiscal;


}
 