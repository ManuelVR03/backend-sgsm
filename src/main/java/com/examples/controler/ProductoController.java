package com.examples.controler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.DTO.ProductoDTO;
import com.examples.DTO.ProductoDTOConverter;
import com.examples.modelo.Producto;
import com.examples.repositorio.ProductoRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apiProductos")
public class ProductoController {

	@Autowired
	private ProductoRepositorio productoRepositorio;
	
	@Autowired
	private ProductoDTOConverter productoDTOConverter;
	
	@GetMapping("/producto")
	public ResponseEntity<?> obtenerTodos() {
		List<Producto> result = productoRepositorio.findAll();
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			List<ProductoDTO> dtoList = result.stream().map(productoDTOConverter::convertirADto)
					.collect(Collectors.toList());
			return ResponseEntity.ok(dtoList);
		}
	}

	@PostMapping("/producto")
	public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO prodDto) {
	    Producto pro = productoDTOConverter.convertirAPro(prodDto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.save(pro));
	}

	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> eliminarProducto(@PathVariable Integer id) {
		if (productoRepositorio.existsById(id)) {
			Producto result = productoRepositorio.findById(id).get();
			productoRepositorio.delete(result);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/producto/{id}")
	public ResponseEntity<?> editarProducto(@PathVariable Integer id, @RequestBody ProductoDTO pro) {
		if (productoRepositorio.existsById(id)) {
			Producto nuevo = productoDTOConverter.convertirAPro(pro);
			nuevo.setId(id);
			Producto actualizado = productoRepositorio.save(nuevo);
			ProductoDTO dto = productoDTOConverter.convertirADto(actualizado);
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/proveedor/{proveedorId}")
	public ResponseEntity<?> obtenerProductosPorProveedor(@PathVariable int proveedorId) {
	    List<Producto> productos = productoRepositorio.findByProveedorId(proveedorId);
	    List<ProductoDTO> dtoList = productos.stream()
	        .map(productoDTOConverter::convertirADto)
	        .collect(Collectors.toList());
	    return ResponseEntity.ok(dtoList);
	}

}
