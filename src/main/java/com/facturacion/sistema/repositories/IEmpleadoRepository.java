package com.facturacion.sistema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.sistema.models.entities.Empleado;
@Repository

public interface IEmpleadoRepository extends JpaRepository <Empleado,Long>{

}
