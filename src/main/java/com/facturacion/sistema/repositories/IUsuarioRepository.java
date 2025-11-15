package com.facturacion.sistema.repositories;
import java.util.Optional;
import com.facturacion.sistema.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository <Usuario,Long> {
	
    Optional<Usuario> findByUsername(String username);

}
