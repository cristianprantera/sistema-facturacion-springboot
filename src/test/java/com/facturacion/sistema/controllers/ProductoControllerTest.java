package com.facturacion.sistema.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.facturacion.sistema.models.dtos.ProductoRequestDTO;
import com.facturacion.sistema.models.dtos.ProductoResponseDTO;
import com.facturacion.sistema.services.interfaces.IProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@WebMvcTest(controllers = ProductoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void mostrarProductoPorId_devuelveProductoCuandoExiste() throws Exception {

        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setNombre("Coca-Cola");
        dto.setPrecio(new BigDecimal("1500"));

        when(productoService.traerProductoPorId(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/productos/1")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Coca-Cola"))
                .andExpect(jsonPath("$.precio").value(1500));

        verify(productoService).traerProductoPorId(1L);
    }


    @Test
    void mostrarProductoPorId_devuelve404CuandoNoExiste() throws Exception {

        when(productoService.traerProductoPorId(99L))
                .thenThrow(new EntityNotFoundException("Producto con el id: 99 no encontrado"));

        mockMvc.perform(get("/api/v1/productos/99")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound());

        verify(productoService).traerProductoPorId(99L);
    }


    @Test
    void crearProducto_devuelve200ConDTOCuandoDatosValidos() throws Exception {

        ProductoRequestDTO requestDTO = new ProductoRequestDTO();
        requestDTO.setCantidadEnStock(150L);
        requestDTO.setNombre("Coca-Cola");
        requestDTO.setPrecio(new BigDecimal("1500"));

        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setNombre("Coca-Cola");
        dto.setPrecio(new BigDecimal("1500"));

        when(productoService.crearProducto(any(ProductoRequestDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Coca-Cola"))
                .andExpect(jsonPath("$.precio").value(1500));

        verify(productoService).crearProducto(any(ProductoRequestDTO.class));
    }


    @Test
    void editarProductoPorId_devuelve200ConProductoActualizadoCuandoExiste() throws Exception {

        ProductoRequestDTO requestDTO = new ProductoRequestDTO();
        requestDTO.setCantidadEnStock(150L);
        requestDTO.setNombre("Coca-Cola");
        requestDTO.setPrecio(new BigDecimal("1500"));

        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setNombre("Coca-Cola");
        dto.setPrecio(new BigDecimal("1500"));

        when(productoService.editarProductoPorId(any(ProductoRequestDTO.class), eq(1L))).thenReturn(dto);

        mockMvc.perform(put("/api/v1/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Coca-Cola"))
                .andExpect(jsonPath("$.precio").value(1500));

        verify(productoService).editarProductoPorId(any(ProductoRequestDTO.class), eq(1L));
    }


    @Test
    void eliminarProductoPorId_devuelve200CuandoExisteElProducto() throws Exception {

        when(productoService.eliminarProductoPorId(1L))
                .thenReturn("Producto con id: 1 eliminado correctamente");

        mockMvc.perform(delete("/api/v1/productos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto con id: 1 eliminado correctamente"));

        verify(productoService).eliminarProductoPorId(1L);
    }
}
