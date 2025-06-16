package com.examples.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.examples.modelo.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Integer>{
	
}
