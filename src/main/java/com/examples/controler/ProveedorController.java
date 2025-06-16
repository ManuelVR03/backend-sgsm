package com.examples.controler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.DTO.ProductoDTO;
import com.examples.DTO.ProductoDTOConverter;
import com.examples.DTO.ProveedorDTO;
import com.examples.DTO.ProveedorDTOConverter;
import com.examples.modelo.Producto;
import com.examples.modelo.Proveedor;
import com.examples.repositorio.ProductoRepositorio;
import com.examples.repositorio.ProveedorRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apiProveedores")
public class ProveedorController {

	@Autowired
	private ProveedorRepositorio proveedorRepositorio;
	
	@Autowired
	private ProveedorDTOConverter proveedorDTOConverter;
	
	@Autowired
	private ProductoRepositorio productoRepositorio;
	
	@Autowired
	private ProductoDTOConverter productoDTOConverter;
	
	@GetMapping("/proveedor")
	public ResponseEntity<?> obtenerTodos() {
		List<Proveedor> result = proveedorRepositorio.findAll();
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			List<ProveedorDTO> dtoList = result.stream().map(proveedorDTOConverter::convertirADto)
					.collect(Collectors.toList());
			return ResponseEntity.ok(dtoList);
		}
	}
	
	@GetMapping("/proveedor/{proveedorId}")
	public ResponseEntity<?> getProductosPorProveedor(@PathVariable int proveedorId) {
	    List<Producto> productos = productoRepositorio.findByProveedorId(proveedorId);
	    List<ProductoDTO> dtoList = productos.stream().map(productoDTOConverter::convertirADto).collect(Collectors.toList());
	    return ResponseEntity.ok(dtoList);
	}

}
