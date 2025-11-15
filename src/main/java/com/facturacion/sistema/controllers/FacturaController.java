package com.facturacion.sistema.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.sistema.models.dtos.FacturaRequestDTO;
import com.facturacion.sistema.models.dtos.FacturaResponseDTO;
import com.facturacion.sistema.services.interfaces.IFacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Facturas", description = "Creación y consulta de facturas")
@RestController
@RequestMapping("/api/v1/facturas")
public class FacturaController {

    private final IFacturaService facturaService;

    public FacturaController(IFacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @Operation(
	    summary = "Crear una factura",
	    description = "Genera una factura con cliente, empleado, sucursal y una lista de productos (detalles). Actualiza stock automáticamente."
    )
    @ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Factura creada correctamente"),
	    @ApiResponse(responseCode = "400", description = "Datos inválidos o falta de stock")
    })
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PostMapping
    public ResponseEntity<FacturaResponseDTO> crearFactura(@RequestBody FacturaRequestDTO request) {
        FacturaResponseDTO response = facturaService.crearFactura(request);
        return ResponseEntity.ok(response);
    }
    
    @Operation(
	    summary = "Obtener factura por ID",
	    description = "Devuelve los datos completos de una factura existente, incluyendo sus detalles."
    )
    @ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Factura encontrada"),
	    @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })   
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @GetMapping("/{idFactura}")
    public ResponseEntity<FacturaResponseDTO> obtenerFactura(@PathVariable Long idFactura) {
        return ResponseEntity.ok(facturaService.obtenerFacturaPorId(idFactura));
    }
}
