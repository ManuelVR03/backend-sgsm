package com.examples.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.examples.modelo.Pedido;

public interface GenerarOdsRepositorio extends JpaRepository<Pedido, Integer> {

	@Query("SELECT p.bar.nombre, COUNT(p.id) FROM Pedido p WHERE p.fechaRealizacion BETWEEN :inicio AND :fin GROUP BY p.bar.nombre")
	List<Object[]> contarPedidosPorBar(@Param("inicio") Date inicio, @Param("fin") Date fin);

	@Query("SELECT p.proveedor.nombre, COUNT(p.id) " + "FROM Pedido p "
			+ "WHERE p.fechaRealizacion BETWEEN :inicio AND :fin " + "GROUP BY p.proveedor.nombre")
	List<Object[]> contarPedidosPorProveedor(@Param("inicio") Date inicio, @Param("fin") Date fin);

}
