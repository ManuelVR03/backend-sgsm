package com.examples.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbaranProductoDTO {

	private String referencia;
    private String descripcion;
    private BigDecimal cantidad;
    private BigDecimal precio;
}
