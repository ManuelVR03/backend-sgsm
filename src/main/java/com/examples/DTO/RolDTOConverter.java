package com.examples.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examples.modelo.Rol;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RolDTOConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public RolDTO convertirADto(Rol rol) {
		return modelMapper.map(rol, RolDTO.class);
	}
}
