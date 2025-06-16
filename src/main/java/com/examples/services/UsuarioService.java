package com.examples.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.examples.modelo.Usuario;
import com.examples.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepositorio usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convertir el rol a GrantedAuthority
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol().getNombre());

        return new User(
                usuario.getDni(),
                usuario.getPass(), // contraseña cifrada con BCrypt
                Collections.singletonList(authority)
        );
    }
	
	public Usuario getUsuarioByDni(String dni) {
	    return usuarioRepository.findByDni(dni)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con dni: " + dni));
	}

}
