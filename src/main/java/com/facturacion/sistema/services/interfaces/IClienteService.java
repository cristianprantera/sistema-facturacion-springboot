package com.facturacion.sistema.services.interfaces;

import com.facturacion.sistema.models.dtos.ClienteRequestDTO;
import com.facturacion.sistema.models.dtos.ClienteResponseDTO;

public interface IClienteService {
	ClienteResponseDTO traerClientePorId(Long id);
	ClienteResponseDTO crearCliente(ClienteRequestDTO clienteRequest);
	ClienteResponseDTO editarClienteConId(ClienteRequestDTO clienteRequest,Long id);
	String eliminarClienteConId(Long id);

}
