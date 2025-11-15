package com.facturacion.sistema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.facturacion.sistema.models.entities.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository <Cliente,Long> {
	

}
