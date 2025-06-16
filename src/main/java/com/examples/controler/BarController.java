package com.examples.controler;

import com.examples.DTO.BarDTO;
import com.examples.DTO.BarDTOConverter;
import com.examples.modelo.Bar;
import com.examples.repositorio.BarRepositorio;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apiBares")
@RequiredArgsConstructor
public class BarController {

	@Autowired
	private BarRepositorio barRepositorio;

	@Autowired
	private BarDTOConverter barDTOConverter;

	@GetMapping("/bar")
	public ResponseEntity<?> obtenerTodos() {
		List<Bar> result = barRepositorio.findAll();
		List<BarDTO> dtoList = result.stream().map(barDTOConverter::convertirADto).collect(Collectors.toList());
		return ResponseEntity.ok(dtoList);
	}

	@GetMapping("/bar/{id}")
	public ResponseEntity<?> getBarById(@PathVariable int id) {
		return barRepositorio.findById(id).map(bar -> {
			BarDTO dto = barDTOConverter.convertirADto(bar);
			return ResponseEntity.ok(dto);
		}).orElse(ResponseEntity.notFound().build());
	}

}
