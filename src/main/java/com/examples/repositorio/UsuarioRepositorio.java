package com.examples.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.examples.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario> findByDni(String dni);
	
}
