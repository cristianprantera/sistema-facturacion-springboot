package com.facturacion.sistema.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.sistema.models.dtos.ClienteRequestDTO;
import com.facturacion.sistema.models.dtos.ClienteResponseDTO;
import com.facturacion.sistema.services.interfaces.IClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Clientes", description = "Gestión de clientes del sistema")
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @Operation(
        summary = "Obtener cliente por ID",
    	description = "Devuelve un cliente registrado en el sistema según su ID."
    )
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "Cliente encontrado"),
    	@ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<ClienteResponseDTO> traerCliente(@PathVariable Long id) {
        ClienteResponseDTO dto = clienteService.traerClientePorId(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
    	summary = "Crear un nuevo cliente",
    	description = "Registra un nuevo cliente en el sistema con nombre, apellido, DNI, email, celular y responsabilidad fiscal."
    )
    @ApiResponses({
    	@ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
    	@ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud")
    }) 
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody @Valid ClienteRequestDTO clienteRequest) {
        ClienteResponseDTO creado = clienteService.crearCliente(clienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(
	    summary = "Editar cliente por ID",
	    description = "Modifica los datos de un cliente existente según su ID."
    )
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
    	@ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    }) 
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> modificarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequest, @PathVariable Long id) {
        ClienteResponseDTO actualizado = clienteService.editarClienteConId(clienteRequest, id);
        return ResponseEntity.ok(actualizado);
    }
    
    @Operation(
	    summary = "Eliminar cliente por ID",
	    description = "Elimina un cliente del sistema según su ID."
    )
    @ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
	    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarClienteConId(@PathVariable Long id) {
        String mensaje = clienteService.eliminarClienteConId(id);
        return ResponseEntity.ok(mensaje);
    }
}
