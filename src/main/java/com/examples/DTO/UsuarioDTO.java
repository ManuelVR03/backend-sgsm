package com.examples.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
	private String id;
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String pass;
	private RolDTO rol;
	private BarDTO bar;
}
