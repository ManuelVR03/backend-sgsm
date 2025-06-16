package com.examples.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.examples.modelo.EstadoPedido;

public interface EstadoPedidoRepositorio extends JpaRepository<EstadoPedido, Integer>{
	EstadoPedido findByNombre(String nombre);
}
