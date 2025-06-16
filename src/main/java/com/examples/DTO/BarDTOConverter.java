package com.examples.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examples.modelo.Bar;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BarDTOConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public BarDTO convertirADto(Bar bar) {
		return modelMapper.map(bar, BarDTO.class);
	}
}
