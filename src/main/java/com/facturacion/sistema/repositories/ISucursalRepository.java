package com.facturacion.sistema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.sistema.models.entities.Sucursal;

@Repository
public interface ISucursalRepository extends JpaRepository <Sucursal,Long> {

}
