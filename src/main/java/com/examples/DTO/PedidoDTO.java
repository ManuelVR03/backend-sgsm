package com.examples.DTO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

	private int id;
	private String barNombre;
	private String usuarioNombre;
	private Date fechaRealizacion;
	private String proveedorNombre;
	private String estadoPedidoNombre;
	private Date fechaRecepcion;
}
