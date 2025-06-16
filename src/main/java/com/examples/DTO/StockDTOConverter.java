package com.examples.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examples.modelo.Stock;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockDTOConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public StockDTO convertirADto(Stock stock) {
		return modelMapper.map(stock, StockDTO.class);
	}
	
	public Stock convertirAStock(StockDTO stock) {
		return modelMapper.map(stock, Stock.class);
	}
}
