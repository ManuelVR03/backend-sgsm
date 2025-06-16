package com.examples.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.examples.modelo.Albaran;

public interface AlbaranRepositorio extends JpaRepository<Albaran, Integer> {
	List<Albaran> findByPedidoProveedorIdAndFechaGeneracionBetweenAndValidadoFalse(
		    int proveedorId, Date inicio, Date fin);

}
