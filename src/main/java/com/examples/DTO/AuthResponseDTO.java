package com.examples.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponseDTO {

	private String token;
	private String rol;
	private int idUsuario;
	private Integer barId;
}
