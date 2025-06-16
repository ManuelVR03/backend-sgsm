package com.examples.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NuevoPedidoDTO {

	private int barId;
    private int usuarioId;
    private int proveedorId;
    private List<DetalleProductoDTO> productos;
}
