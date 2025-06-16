package com.examples.DTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbaranDTO {

	private int id;
    private Date fechaGeneracion;
    private BigDecimal importeTotal;
    private boolean validado;
    private int proveedorId;
    private String proveedorNombre;
    private String proveedorCif;
    private String proveedorDireccion;
    private String proveedorTelefono;
    private String proveedorEmail;
    private String clienteNombre;
    private String clienteDireccion;
    private String clienteNif;
    private int pedidoId;
    private List<AlbaranProductoDTO> productos;
}
