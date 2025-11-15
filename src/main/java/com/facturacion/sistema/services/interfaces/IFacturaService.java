package com.facturacion.sistema.services.interfaces;

import com.facturacion.sistema.models.dtos.FacturaRequestDTO;
import com.facturacion.sistema.models.dtos.FacturaResponseDTO;

public interface IFacturaService {
    FacturaResponseDTO crearFactura(FacturaRequestDTO facturaRequest);
	FacturaResponseDTO obtenerFacturaPorId(Long idFactura);

}
