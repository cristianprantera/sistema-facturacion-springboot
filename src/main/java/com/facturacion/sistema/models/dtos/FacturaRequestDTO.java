package com.facturacion.sistema.models.dtos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@AllArgsConstructor
@Getter @Setter
public class FacturaRequestDTO {

    @NotNull(message = "El id del cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "El id del empleado es obligatorio")
    private Long idEmpleado;

    @NotNull(message = "El id de la sucursal es obligatorio")
    private Long idSucursal;

    @NotEmpty(message = "La factura debe contener al menos un detalle")
    @Size(min = 1, message = "La factura debe contener al menos un producto")
    @Valid
    private List<DetalleFacturaRequestDTO> detalles;
}
