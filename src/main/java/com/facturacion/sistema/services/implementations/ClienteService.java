package com.facturacion.sistema.services.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.sistema.models.dtos.ClienteRequestDTO;
import com.facturacion.sistema.models.dtos.ClienteResponseDTO;
import com.facturacion.sistema.models.entities.Cliente;
import com.facturacion.sistema.models.enums.TipoResponsabilidadFiscal;
import com.facturacion.sistema.repositories.IClienteRepository;
import com.facturacion.sistema.services.interfaces.IClienteService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j

public class ClienteService implements IClienteService{
	private final IClienteRepository clienteRepo;
	
	public ClienteService(IClienteRepository clienteRepo) {
		this.clienteRepo=clienteRepo;
	}
	
	@Override
	public ClienteResponseDTO traerClientePorId(Long id) {
		Cliente cliente= encontrarCliente(id);			
		ClienteResponseDTO responseDTO= mapearClienteAResponse(cliente);
		
		return responseDTO;	
	}
	
	@Override
	public ClienteResponseDTO crearCliente(ClienteRequestDTO clienteRequest) {
        log.info("Creando cliente {}", clienteRequest.getNombre());

		Cliente cliente=mapearRequestACLiente(clienteRequest);
		
		Cliente clienteSave= clienteRepo.save(cliente);
		
		ClienteResponseDTO responseDTO= mapearClienteAResponse(clienteSave);
			
		return responseDTO;
	}
	
	@Override
	public ClienteResponseDTO editarClienteConId(ClienteRequestDTO clienteRequest,Long id) {
        log.info("Editando cliente ID {}", id);
		Cliente cliente= encontrarCliente(id);	
		
		cliente.setApellido(clienteRequest.getApellido());
		cliente.setDni(clienteRequest.getDni());
		cliente.setEmail(clienteRequest.getEmail());
		cliente.setNombre(clienteRequest.getNombre());
		cliente.setNroCelular(clienteRequest.getNroCelular());
		cliente.setTipoResponsabilidadFiscal(TipoResponsabilidadFiscal.valueOf(clienteRequest.getTipoResponsabilidadFiscal()));
		
		Cliente clienteSave=clienteRepo.save(cliente);
		
		ClienteResponseDTO response= mapearClienteAResponse(clienteSave);
		
		return response;
		
	}
	
	@Override
	public String eliminarClienteConId(Long id) {
		Cliente cliente=encontrarCliente(id);
		
		clienteRepo.delete(cliente);
		
        log.info("Cliente con ID {} eliminado correctamente", id);
		return "Cliente con ID: "+id+ ", eliminado correctamente";
		
	}
	
	private ClienteResponseDTO mapearClienteAResponse(Cliente cliente) {
		ClienteResponseDTO responseDTO= new ClienteResponseDTO();
		
		responseDTO.setDni(cliente.getDni());
		responseDTO.setEmail(cliente.getEmail());
		responseDTO.setNombreYApellido(cliente.getNombre()+" "+cliente.getApellido());
		responseDTO.setNroCelular(cliente.getNroCelular());
		responseDTO.setTipoResponsabilidadFiscal(cliente.getTipoResponsabilidadFiscal().toString());
		
		return responseDTO;
	}
	
	private Cliente mapearRequestACLiente(ClienteRequestDTO clienteRequest) {
		
		Cliente cliente=new Cliente();

		cliente.setApellido(clienteRequest.getApellido());
		cliente.setDni(clienteRequest.getDni());
		cliente.setEmail(clienteRequest.getEmail());
		cliente.setNombre(clienteRequest.getNombre());
		cliente.setNroCelular(clienteRequest.getNroCelular());
		cliente.setTipoResponsabilidadFiscal(TipoResponsabilidadFiscal.valueOf(clienteRequest.getTipoResponsabilidadFiscal()));
		
		return cliente;
	}
	
	private Cliente encontrarCliente(Long id) {
		Cliente cliente= clienteRepo.findById(id)
		.orElseThrow(()-> new EntityNotFoundException("Cliente con el id: "+id+", no encontrado"));	
		
		return cliente;
	}
}
