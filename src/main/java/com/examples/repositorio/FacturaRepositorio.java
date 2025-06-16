package com.examples.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.examples.modelo.Factura;

public interface FacturaRepositorio extends JpaRepository<Factura, Integer>{
	
}
