package com.facturacion.sistema.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.facturacion.sistema.models.entities.Usuario;
import com.facturacion.sistema.repositories.IUsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final IUsuarioRepository usuarioRepo;
	
	public CustomUserDetailsService(IUsuarioRepository usuarioRepo) {
		this.usuarioRepo=usuarioRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
		Usuario usuario = usuarioRepo.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + username));
		
		SimpleGrantedAuthority authority=
				new SimpleGrantedAuthority("ROLE_"+usuario.getRol().name());
		
		return new User(
					usuario.getUsername(),
					usuario.getPassword(),
					Collections.singletonList(authority)			
		);		
	}
}
 