package com.facturacion.sistema.services.interfaces;

import org.springframework.data.domain.Page;

import com.facturacion.sistema.models.dtos.ProductoRequestDTO;
import com.facturacion.sistema.models.dtos.ProductoResponseDTO;
import com.facturacion.sistema.models.entities.Producto;

public interface IProductoService {
	ProductoResponseDTO traerProductoPorId(Long id);
	ProductoResponseDTO crearProducto(ProductoRequestDTO productoRequest);
	ProductoResponseDTO editarProductoPorId(ProductoRequestDTO producto,Long id);
	String eliminarProductoPorId(Long id);
	Page<ProductoResponseDTO> listarPaginado(int page, int size);

}
