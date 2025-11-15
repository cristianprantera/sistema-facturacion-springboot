package com.facturacion.sistema.services.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.facturacion.sistema.models.dtos.ProductoRequestDTO;
import com.facturacion.sistema.models.dtos.ProductoResponseDTO;
import com.facturacion.sistema.models.entities.Producto;
import com.facturacion.sistema.repositories.IProductoRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
	
	@Mock
	private IProductoRepository productoRepo;
	
	@InjectMocks
	private ProductoService productoService;

	
	@Test
	void traerProductoPorId_devuelveProductoCuandoExiste() {
		
		Producto producto=new Producto();
		producto.setId(1L);
		producto.setNombre("Coca-Cola");
		producto.setPrecio(new BigDecimal("1500"));
		
		when(productoRepo.findById(1L)).thenReturn(Optional.of(producto));
		
		ProductoResponseDTO response = productoService.traerProductoPorId(1L);
		
		assertNotNull(response);
		assertEquals("Coca-Cola", response.getNombre());
		assertEquals(new BigDecimal("1500"), response.getPrecio());
		
		verify(productoRepo).findById(1L);

	}
	
	
	@Test
	void traerProductoPorId_lanzaExcepcionSiNoExiste() {
		when(productoRepo.findById(99L)).thenReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> {
		    productoService.traerProductoPorId(99L);
		});
		verify(productoRepo).findById(99L);

	}
	
    @Test
    void crearProducto_devuelveProductoCuandoSeCreaBien() {
        // 1️⃣ ARRANGE (preparar el escenario)

        // Request que llega desde el controller / API
        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Coca-Cola");
        request.setPrecio(new BigDecimal("1500"));
        request.setCantidadEnStock(10L);

        // Producto que simula lo que guardaría la base de datos
        Producto productoGuardado = new Producto();
        productoGuardado.setId(1L); // simula que la BD generó el ID
        productoGuardado.setNombre("Coca-Cola");
        productoGuardado.setPrecio(new BigDecimal("1500"));
        productoGuardado.setCantidadEnStock(10L);

        // Cuando alguien llame a productoRepo.save(...) → devolvé productoGuardado
        when(productoRepo.save(any(Producto.class))).thenReturn(productoGuardado);

        // 2️⃣ ACT (ejecutar el método real del Service)
        ProductoResponseDTO response = productoService.crearProducto(request);

        // 3️⃣ ASSERT (verificar que todo esté correcto)
        assertNotNull(response);
        assertEquals("Coca-Cola", response.getNombre());
        assertEquals(new BigDecimal("1500"), response.getPrecio());

        // Verifico que se llamó a save() una sola vez
        verify(productoRepo, times(1)).save(any(Producto.class));
    }
    
    @Test
    void editarProductoPorId_devuelveProductoActualizadoCuandoExiste() {
    	
        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Coca-Cola");
        request.setPrecio(new BigDecimal("1500"));
        request.setCantidadEnStock(15L);
    	
        Producto productoGuardado = new Producto();
        productoGuardado.setId(1L); 
        productoGuardado.setNombre("Coca-Cola");
        productoGuardado.setPrecio(new BigDecimal("1500"));
        productoGuardado.setCantidadEnStock(10L);
        
		when(productoRepo.findById(1L)).thenReturn(Optional.of(productoGuardado));
        when(productoRepo.save(any(Producto.class))).thenReturn(productoGuardado);

        ProductoResponseDTO response = productoService.editarProductoPorId(request,1L);

        assertNotNull(response);
        assertEquals("Coca-Cola", response.getNombre());
        assertEquals(new BigDecimal("1500"), response.getPrecio());

        verify(productoRepo).findById(1L);
        verify(productoRepo).save(any(Producto.class));
    }

    @Test
    void editarProductoPorId_lanzaExcepcionCuandoNoExiste() {
    	
        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Coca-Cola");
        request.setPrecio(new BigDecimal("1500"));
        request.setCantidadEnStock(15L);
        
		when(productoRepo.findById(99L)).thenReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> {
		    productoService.editarProductoPorId(request, 99L);
		});
		
		verify(productoRepo).findById(99L);		
    }
    
    @Test
    void eliminarProductoPorId_eliminaCuandoExiste() {
    	
		Producto producto=new Producto();
		producto.setId(1L);
		producto.setNombre("Coca-Cola");
		producto.setPrecio(new BigDecimal("1500"));
		
		when(productoRepo.findById(1L)).thenReturn(Optional.of(producto));
		
		String respuesta=productoService.eliminarProductoPorId(1L);
		
		verify(productoRepo).findById(1L);	
		verify(productoRepo).delete(producto);
		assertEquals("Producto con id: 1 eliminado correctamente",respuesta);

    }
    
   @Test
   void eliminarProductoPorId_lanzaExcepcionCuandoNoExiste() {
		when(productoRepo.findById(99L)).thenReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> {
		    productoService.eliminarProductoPorId(99L);
		});
		
		verify(productoRepo).findById(99L);	
		verify(productoRepo, never()).delete(any(Producto.class));

   }

}
