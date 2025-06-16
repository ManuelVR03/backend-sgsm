package com.examples.controler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.DTO.StockDTO;
import com.examples.DTO.StockDTOConverter;
import com.examples.modelo.Stock;
import com.examples.repositorio.StockRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apiStock")
public class StockController {

	@Autowired
	private StockRepositorio stockRepositorio;

	@Autowired
	private StockDTOConverter stockDTOConverter;

	@GetMapping("/bar/{barId}")
	public ResponseEntity<?> getStockByBarId(@PathVariable int barId) {
		List<Stock> stockList = stockRepositorio.findByBarId(barId);
		List<StockDTO> dtoList = stockList.stream().map(stockDTOConverter::convertirADto).collect(Collectors.toList());
		return ResponseEntity.ok(dtoList);

	}
	
	@PutMapping("/modificar")
	public ResponseEntity<?> actualizarMultiplesStock(@RequestBody List<StockDTO> cambios) {
	    for (StockDTO dto : cambios) {
	        Optional<Stock> optStock = stockRepositorio.findByBarIdAndProductoId(dto.getBarId(), dto.getProductoId());

	        if (optStock.isPresent()) {
	            Stock stock = optStock.get();
	            stock.setCantidad(BigDecimal.valueOf(dto.getCantidad()));
	            stockRepositorio.save(stock);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Stock no encontrado para barId: " + dto.getBarId() + ", productoId: " + dto.getProductoId());
	        }
	    }

	    return ResponseEntity.ok("Stock actualizado correctamente.");
	}


}
