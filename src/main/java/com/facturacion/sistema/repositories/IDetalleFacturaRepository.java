package com.facturacion.sistema.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.sistema.models.entities.DetalleFactura;
import com.facturacion.sistema.models.entities.Factura;

public interface IDetalleFacturaRepository extends JpaRepository <DetalleFactura,Long>{

	List<DetalleFactura> findByFactura(Factura factura);

}
