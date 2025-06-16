package com.examples.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.examples.modelo.Stock;

public interface StockRepositorio extends JpaRepository<Stock, Integer>{
	
	List<Stock> findByBarId(int barId);
	Optional<Stock> findByBarIdAndProductoId(int barId, int productoId);
	@Query("SELECT s.producto.nombre, SUM(s.cantidad) FROM Stock s GROUP BY s.producto.nombre")
	List<Object[]> obtenerStockTotalPorProducto();

}
