package com.facturacion.sistema.services.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.sistema.models.dtos.DetalleFacturaRequestDTO;
import com.facturacion.sistema.models.dtos.DetalleFacturaResponseDTO;
import com.facturacion.sistema.models.dtos.FacturaRequestDTO;
import com.facturacion.sistema.models.dtos.FacturaResponseDTO;
import com.facturacion.sistema.models.entities.Cliente;
import com.facturacion.sistema.models.entities.DetalleFactura;
import com.facturacion.sistema.models.entities.Empleado;
import com.facturacion.sistema.models.entities.Factura;
import com.facturacion.sistema.models.entities.Producto;
import com.facturacion.sistema.models.entities.Sucursal;
import com.facturacion.sistema.repositories.IClienteRepository;
import com.facturacion.sistema.repositories.IDetalleFacturaRepository;
import com.facturacion.sistema.repositories.IEmpleadoRepository;
import com.facturacion.sistema.repositories.IFacturaRepository;
import com.facturacion.sistema.repositories.IProductoRepository;
import com.facturacion.sistema.repositories.ISucursalRepository;
import com.facturacion.sistema.services.interfaces.IFacturaService;
import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class FacturaService implements IFacturaService{
	private final IClienteRepository clienteRepo;
	private final IEmpleadoRepository empleadoRepo;
	private final IDetalleFacturaRepository detalleFacturaRepo;
	private final IProductoRepository productoRepo;
	private final ISucursalRepository sucursalRepo;
	private final IFacturaRepository facturaRepo;
	
	public FacturaService(IClienteRepository clienteRepo, IEmpleadoRepository empleadoRepo,
			IDetalleFacturaRepository detalleFacturaRepo, IProductoRepository productoRepo, ISucursalRepository sucursalRepo, IFacturaRepository facturaRepo) {
		this.clienteRepo = clienteRepo;
		this.empleadoRepo = empleadoRepo;
		this.detalleFacturaRepo = detalleFacturaRepo;
		this.productoRepo = productoRepo;
		this.sucursalRepo = sucursalRepo;
		this.facturaRepo = facturaRepo;
	}
	
	@Override
	public FacturaResponseDTO crearFactura(FacturaRequestDTO facturaRequest) {

	    // Validar que la factura tenga al menos un detalle
	    validarFacturaRequest(facturaRequest);

        log.info("Creando factura para cliente {}...", facturaRequest.getIdCliente());

	    // Obtener entidades necesarias desde la base de datos
	    Cliente cliente = traerCliente(facturaRequest.getIdCliente());
	    Empleado empleado = traerEmpleado(facturaRequest.getIdEmpleado());
	    Sucursal sucursal = traerSucursal(facturaRequest.getIdSucursal());

	    // Crear entidad Factura con los datos recibidos
	    Factura factura = mapearFacturaRequestAEntidad(facturaRequest, cliente, empleado, sucursal);

	    // Guardar factura en la base de datos (ya genera ID)
	    Factura facturaGuardada = facturaRepo.save(factura);

	    // Crear los detalles de la factura (uno por producto)
	    List<DetalleFactura> listDetalle = crearDetallesFactura(facturaRequest, facturaGuardada);

	    // Guardar los detalles de factura en la base de datos
	    List<DetalleFactura> listGuardada = detalleFacturaRepo.saveAll(listDetalle);

	    // Convertir Factura entidad ➝ FacturaResponseDTO
	    FacturaResponseDTO responseDTO = mapearFacturaAResponse(facturaGuardada);

	    // Convertir detalles ➝ DetalleFacturaResponseDTO y agregarlos al response
	    List<DetalleFacturaResponseDTO> lstResponse = mapearDetalleFacturaAResponse(listGuardada);
	    responseDTO.setDetalles(lstResponse);

	    // Devolver respuesta final al controlador (API)
        log.info("Factura {} creada exitosamente.", facturaGuardada.getId());
	    return responseDTO;
	}

	@Override
	public FacturaResponseDTO obtenerFacturaPorId(Long idFactura) {
        log.info("Buscando factura {}", idFactura);

		Factura factura= facturaRepo.findById(idFactura)
				.orElseThrow(()-> new EntityNotFoundException("Factura con id: "+idFactura+" no encontrado"));
		FacturaResponseDTO responseDTO= mapearFacturaAResponse(factura);
		List<DetalleFactura> lstDetalleFactura=detalleFacturaRepo.findByFactura(factura);
		List<DetalleFacturaResponseDTO> lstDetalleFacturaResponseDTO=mapearDetalleFacturaAResponse(lstDetalleFactura);
		responseDTO.setDetalles(lstDetalleFacturaResponseDTO);
		return responseDTO;
		
	}

	private void validarFacturaRequest(FacturaRequestDTO facturaRequest) {
	    if (facturaRequest.getDetalles() == null || facturaRequest.getDetalles().isEmpty()) {
	        throw new IllegalArgumentException("La factura debe tener al menos un detalle.");
	    }
	}
	
	private Cliente traerCliente(Long idCliente) {
	    return clienteRepo.findById(idCliente)
	            .orElseThrow(() -> new EntityNotFoundException("Cliente con id: " + idCliente + " no encontrado"));
	}
	
	private Empleado traerEmpleado(Long idEmpleado) {
		return empleadoRepo.findById(idEmpleado)
		.orElseThrow(() -> new EntityNotFoundException("Empleado con id: " + idEmpleado + " no encontrado"));

	}
	
	private Sucursal traerSucursal(Long idSucursal) {
		return sucursalRepo.findById(idSucursal)
		.orElseThrow(() -> new EntityNotFoundException("Sucursal con id: " + idSucursal + " no encontrada"));

	}
	
	private Producto traerProducto(Long idProducto) {
		return productoRepo.findById(idProducto)
		.orElseThrow(() -> new EntityNotFoundException("Producto con id: " + idProducto + " no encontrado"));	
	}
	
	private BigDecimal calcularTotal(List<DetalleFacturaRequestDTO> detalles) {
	    BigDecimal total = BigDecimal.ZERO;
	    for (DetalleFacturaRequestDTO f : detalles) {
	        Producto producto = traerProducto(f.getIdProducto());
	        BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(f.getCantidad()));
	        total = total.add(subtotal);
	    }
	    
	    return total;
	}
	
	private List<DetalleFacturaResponseDTO> mapearDetalleFacturaAResponse(List<DetalleFactura> listGuardada){
	    List<DetalleFacturaResponseDTO> lstResponse = new ArrayList<>();
	    for (DetalleFactura f : listGuardada) {
	        DetalleFacturaResponseDTO detalleDTO = new DetalleFacturaResponseDTO();
	        detalleDTO.setProductoNombre(f.getProducto().getNombre());
	        detalleDTO.setCantidad(f.getCantidad());
	        detalleDTO.setPrecioUnitario(f.getPrecioUnitario());
	        detalleDTO.setSubtotal(f.getSubtotal());
	        lstResponse.add(detalleDTO);
	    }
	    return lstResponse;
	}
	
	
	private FacturaResponseDTO mapearFacturaAResponse(Factura facturaGuardada) {
	    FacturaResponseDTO responseDTO = new FacturaResponseDTO();
	    BigDecimal subtotal = facturaGuardada.getTotal(); 

	    BigDecimal ivaPorcentaje=null;
	    BigDecimal ivaMonto=null;
	    BigDecimal subtotalSinIva = facturaGuardada.getTotal();

	    BigDecimal totalFinal=facturaGuardada.getTotal();
	    
	    
	    if("RESPONSABLE_INSCRIPTO".equals(facturaGuardada.getCliente().getTipoResponsabilidadFiscal().toString())) {
	    	ivaPorcentaje= new BigDecimal("0.21");
	        subtotalSinIva = subtotal.divide(BigDecimal.valueOf(1.21), 2, RoundingMode.HALF_UP);
	        ivaMonto = subtotal.subtract(subtotalSinIva);
	        totalFinal = subtotal; 
	    	
	    }
	    
	    responseDTO.setIdFactura(facturaGuardada.getId());
	    responseDTO.setFecha(facturaGuardada.getFechaHora().toLocalDate());
	    responseDTO.setHora(facturaGuardada.getFechaHora().toLocalTime());
	    responseDTO.setSubtotal(facturaGuardada.getTotal());
	    responseDTO.setClienteNombre(facturaGuardada.getCliente().getNombre() + " " + facturaGuardada.getCliente().getApellido());
	    responseDTO.setEmpleadoNombre(facturaGuardada.getEmpleado().getNombre() + " " + facturaGuardada.getEmpleado().getApellido());
	    responseDTO.setEmpleadoLegajo(facturaGuardada.getEmpleado().getLegajo());
	    responseDTO.setSucursalNombre(facturaGuardada.getSucursal().getNombre());
	    responseDTO.setIvaPorcentaje(ivaPorcentaje);
	    responseDTO.setIvaMonto(ivaMonto);
	    responseDTO.setTotalFinal(totalFinal);
	    responseDTO.setClienteResponsabilidadFiscal(facturaGuardada.getCliente().getTipoResponsabilidadFiscal().toString());
	    
	    return responseDTO;
	}
	
	private Factura mapearFacturaRequestAEntidad(FacturaRequestDTO request, Cliente cliente,  Empleado empleado, Sucursal sucursal) {
		Factura factura = new Factura();
		factura.setCliente(cliente);
		factura.setEmpleado(empleado);
		factura.setSucursal(sucursal);
		factura.setFechaHora(LocalDateTime.now());
		factura.setTotal(calcularTotal(request.getDetalles()));
		return factura;
	}


	 
	
	private List<DetalleFactura> crearDetallesFactura(FacturaRequestDTO facturaRequest,Factura facturaGuardada){
	
	    List<DetalleFactura> listDetalle = new ArrayList<>();
	    
	    for (DetalleFacturaRequestDTO f : facturaRequest.getDetalles()) {
	    	
	        DetalleFactura detalleFactura = new DetalleFactura();
	        Producto producto = traerProducto(f.getIdProducto());
	        
	        if (producto.getCantidadEnStock() <= 0) {
	            throw new IllegalArgumentException("No hay stock disponible de: " + producto.getNombre());
	        }
     
	        if (producto.getCantidadEnStock() < f.getCantidad()) {
	            throw new IllegalArgumentException("No hay stock suficiente de: " + producto.getNombre());
	        }    
	        producto.setCantidadEnStock(producto.getCantidadEnStock() - f.getCantidad());
	        productoRepo.save(producto);

	        detalleFactura.setFactura(facturaGuardada); // Relación con factura
	        detalleFactura.setProducto(producto);
	        detalleFactura.setCantidad(f.getCantidad());
	        detalleFactura.setPrecioUnitario(producto.getPrecio());
	        detalleFactura.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(f.getCantidad())));
	
	        listDetalle.add(detalleFactura);	       	        
	    }
	    
	    return listDetalle;
	}
}
	
	
