package com.facturacion.sistema.models.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class DetalleFacturaResponseDTO {

    private String productoNombre;
    private Long cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
