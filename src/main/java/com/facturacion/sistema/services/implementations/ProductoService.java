package com.facturacion.sistema.services.implementations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.sistema.models.dtos.ProductoRequestDTO;
import com.facturacion.sistema.models.dtos.ProductoResponseDTO;
import com.facturacion.sistema.models.entities.Producto;
import com.facturacion.sistema.repositories.IProductoRepository;
import com.facturacion.sistema.services.interfaces.IProductoService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProductoService implements IProductoService {
	private final IProductoRepository productoRepo;

	public ProductoService(IProductoRepository productoRepo) {
		this.productoRepo = productoRepo;
	}
	
	@Override
	public ProductoResponseDTO traerProductoPorId(Long id) {
        log.info("Buscando producto con ID {}", id);
		Producto producto=productoRepo.findById(id)
		.orElseThrow(()-> new EntityNotFoundException("Producto con el id: "+ id +" no encontrado"));
		
		ProductoResponseDTO responseDTO=new ProductoResponseDTO();
		responseDTO.setNombre(producto.getNombre());
		responseDTO.setPrecio(producto.getPrecio());
		
		return responseDTO;
	}
	
	@Override
	public ProductoResponseDTO crearProducto(ProductoRequestDTO producto) {
        log.info("Creando producto {}", producto.getNombre());
		Producto productoEntidad= new Producto();
		
		productoEntidad.setCantidadEnStock(producto.getCantidadEnStock());
		productoEntidad.setNombre(producto.getNombre());
		productoEntidad.setPrecio(producto.getPrecio());
		
		Producto productoSave=productoRepo.save(productoEntidad);
		
		ProductoResponseDTO productoResponseDTO= new ProductoResponseDTO();
		
		productoResponseDTO.setNombre(productoSave.getNombre());
		productoResponseDTO.setPrecio(productoSave.getPrecio());
		
		return productoResponseDTO;
	}
	
	@Override
	public ProductoResponseDTO editarProductoPorId(ProductoRequestDTO producto,Long id) {
        log.info("Editando producto con ID {}", id);
		Producto productoEntidadAEditar=productoRepo.findById(id)
		.orElseThrow(()-> new EntityNotFoundException("Producto con el id: "+ id +" no encontrado"));
		
		productoEntidadAEditar.setCantidadEnStock(producto.getCantidadEnStock());
		productoEntidadAEditar.setNombre(producto.getNombre());
		productoEntidadAEditar.setPrecio(producto.getPrecio());
		
		Producto productoSave=productoRepo.save(productoEntidadAEditar);

		ProductoResponseDTO productoResponseDTO= new ProductoResponseDTO();
		productoResponseDTO.setNombre(productoSave.getNombre());
		productoResponseDTO.setPrecio(productoSave.getPrecio());
		
		return productoResponseDTO;
		

	}
	
	@Override
	public String eliminarProductoPorId(Long id) {
        log.info("Eliminando producto con ID {}", id);
		Producto producto=productoRepo.findById(id)
		.orElseThrow(()-> new EntityNotFoundException("Producto con el id: "+ id +" no encontrado"));
		
		productoRepo.delete(producto);
		
		return "Producto con id: "+id+" eliminado correctamente";
	}
	
	@Override
	public Page<ProductoResponseDTO> listarPaginado(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size);  // 1) armamos la "página" que queremos
	    Page<Producto> paginaDeProductos = productoRepo.findAll(pageable); // 2) le pedimos eso a la BD

	    // 3) transformamos cada Producto a ProductoResponseDTO
	    Page<ProductoResponseDTO> paginaDTO = paginaDeProductos.map(this::mapearAResponseDTO);

	    return paginaDTO;
	}
	
	private ProductoResponseDTO mapearAResponseDTO(Producto producto) {
	    ProductoResponseDTO dto = new ProductoResponseDTO();
	    dto.setNombre(producto.getNombre());
	    dto.setPrecio(producto.getPrecio());
	    return dto;
	}


	
}
