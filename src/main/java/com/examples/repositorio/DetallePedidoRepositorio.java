package com.examples.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.examples.modelo.DetallePedido;

public interface DetallePedidoRepositorio extends JpaRepository<DetallePedido, Integer>{
	List<DetallePedido> findByPedidoId(int pedidoId);
	@Query("SELECT dp.producto.nombre, SUM(dp.cantidad) " +
		       "FROM DetallePedido dp GROUP BY dp.producto.nombre " +
		       "ORDER BY SUM(dp.cantidad) DESC LIMIT 5")
		List<Object[]> findTopProductosMasPedidos();

}
