package com.examples.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDTO {
	
	private String productoNombre;
	private String productoUnidadMedida;
	private int cantidad;
	private int productoId;
    private int barId;

}
