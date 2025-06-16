package com.examples.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.examples.modelo.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Integer>{

	List<Pedido> findByBarId(int barId);
	
	@Query("SELECT p.bar.nombre, COUNT(p) FROM Pedido p GROUP BY p.bar.nombre")
	List<Object[]> countPedidosGroupByBar();
	
	@Query("SELECT FUNCTION('MONTHNAME', p.fechaRealizacion) as mes, COUNT(p.id) FROM Pedido p GROUP BY FUNCTION('MONTH', p.fechaRealizacion), FUNCTION('MONTHNAME', p.fechaRealizacion) ORDER BY FUNCTION('MONTH', p.fechaRealizacion)")
	List<Object[]> countPedidosPorMes();

}
