package com.facturacion.sistema.controllers;

import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.sistema.models.dtos.ProductoRequestDTO;
import com.facturacion.sistema.models.dtos.ProductoResponseDTO;
import com.facturacion.sistema.services.interfaces.IProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Productos", description = "Operaciones CRUD para la gestión de productos del sistema")
@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    
    private final IProductoService productoService;
    
    public ProductoController(IProductoService productoService){
        this.productoService = productoService;        
    }

    @Operation(
        summary = "Obtener producto por ID",
        description = "Devuelve los datos de un producto registrado según su ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> mostrarProductorPorId(@PathVariable Long id) {
        ProductoResponseDTO dto = productoService.traerProductoPorId(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Crear un nuevo producto",
        description = "Registra un nuevo producto en el sistema incluyendo nombre, precio y stock."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos enviados")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody @Valid ProductoRequestDTO productoRequest) {
        ProductoResponseDTO creado = productoService.crearProducto(productoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(
        summary = "Editar un producto existente",
        description = "Actualiza los datos de un producto según su ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> editarProductoPorId(
            @RequestBody @Valid ProductoRequestDTO productoRequest,
            @PathVariable Long id) {

        ProductoResponseDTO actualizado = productoService.editarProductoPorId(productoRequest, id);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(
        summary = "Eliminar un producto por ID",
        description = "Elimina un producto del sistema según su ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProductoPorId(@PathVariable Long id) {
        String mensaje = productoService.eliminarProductoPorId(id);
        return ResponseEntity.ok(mensaje);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @GetMapping
    public ResponseEntity<Page<ProductoResponseDTO>> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductoResponseDTO> productos = productoService.listarPaginado(page, size);
        return ResponseEntity.ok(productos);
    }

}
