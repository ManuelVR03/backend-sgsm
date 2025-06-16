package com.examples.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examples.modelo.Pedido;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoDTOConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoDTO convertirADto(Pedido pedido) {
		return modelMapper.map(pedido, PedidoDTO.class);
	}
	
	public Pedido convertirAPed(PedidoDTO pedido) {
		return modelMapper.map(pedido, Pedido.class);
	}
}
