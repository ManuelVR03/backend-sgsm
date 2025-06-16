package com.examples.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {
	
	private int id;
	private String nombre;
	private BigDecimal precioUnitario;
	private String unidadMedida;
	private ProveedorDTO proveedor;
	

}
