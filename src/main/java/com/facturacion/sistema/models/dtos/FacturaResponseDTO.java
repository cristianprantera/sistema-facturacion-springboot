package com.facturacion.sistema.models.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class FacturaResponseDTO {

    private Long idFactura;
    private LocalDate fecha;
    private LocalTime hora;
    
    private BigDecimal subtotal;
    private BigDecimal ivaPorcentaje;
    private BigDecimal ivaMonto;
    private BigDecimal totalFinal;

    private String clienteNombre;
    private String clienteResponsabilidadFiscal;
    private String empleadoNombre;
    private Long empleadoLegajo; 
    private String sucursalNombre;

    private List<DetalleFacturaResponseDTO> detalles;
}
