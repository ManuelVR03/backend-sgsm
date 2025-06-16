package com.examples.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.examples.modelo.Proveedor;

public interface ProveedorRepositorio extends JpaRepository<Proveedor, Integer>{

}
