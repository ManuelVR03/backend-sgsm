package com.examples.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examples.modelo.Proveedor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProveedorDTOConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public ProveedorDTO convertirADto(Proveedor proveedor) {
		return modelMapper.map(proveedor, ProveedorDTO.class);
	}
	
	public Proveedor convertirAPro(ProveedorDTO proveedor) {
		return modelMapper.map(proveedor, Proveedor.class);
	}
}
