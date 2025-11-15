package com.facturacion.sistema.services.implementations;

import org.springframework.stereotype.Service;

import com.facturacion.sistema.repositories.IClienteRepository;
import com.facturacion.sistema.repositories.IDetalleFacturaRepository;
import com.facturacion.sistema.repositories.IEmpleadoRepository;
import com.facturacion.sistema.repositories.IProductoRepository;
import com.facturacion.sistema.services.interfaces.IDetalleFacturaService;

@Service
public class DetalleFacturaService implements IDetalleFacturaService {

	private final IDetalleFacturaRepository detalleFacturaRepo;
	private final IProductoRepository productoRepo;
	
	public DetalleFacturaService(IClienteRepository clienteRepo, IEmpleadoRepository empleadoRepo,
			IDetalleFacturaRepository detalleFacturaRepo, IProductoRepository productoRepo) {
		
		this.detalleFacturaRepo = detalleFacturaRepo;
		this.productoRepo = productoRepo;
	}
	
	

}
