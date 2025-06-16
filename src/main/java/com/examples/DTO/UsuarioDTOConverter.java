package com.examples.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examples.modelo.Usuario;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioDTOConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO convertirADto(Usuario usuario) {
		return modelMapper.map(usuario,UsuarioDTO.class);
	}
	
	public Usuario convertirAUsu(UsuarioDTO usuario) {
		return modelMapper.map(usuario, Usuario.class);
	}
}
