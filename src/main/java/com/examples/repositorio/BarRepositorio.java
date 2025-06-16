package com.examples.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.examples.modelo.Bar;

public interface BarRepositorio extends JpaRepository<Bar, Integer>{
	
}
