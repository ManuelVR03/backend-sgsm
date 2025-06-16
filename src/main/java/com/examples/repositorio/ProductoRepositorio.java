package com.examples.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.examples.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer>{
	List<Producto> findByProveedorId(int proveedorId);

}
