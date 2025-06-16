package com.examples.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class DetalleProductoDTO {

	private int productoId;
    private BigDecimal cantidad;
}
