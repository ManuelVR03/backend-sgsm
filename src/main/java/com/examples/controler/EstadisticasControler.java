package com.examples.controler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examples.repositorio.DetallePedidoRepositorio;
import com.examples.repositorio.PedidoRepositorio;
import com.examples.repositorio.StockRepositorio;
import com.examples.services.GenerarOdsService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apiEstadisticas")
public class EstadisticasControler {

	@Autowired
	private PedidoRepositorio pedidoRepositorio;
	
	@Autowired
	private StockRepositorio stockRepositorio;
	
	@Autowired
	private DetallePedidoRepositorio detallePedidoRepositorio;
	
	@Autowired
	private GenerarOdsService generarOdsService;

	@GetMapping("/pedidosBar")
	public ResponseEntity<?> getPedidosPorBar() {
	    List<Object[]> resultados = pedidoRepositorio.countPedidosGroupByBar();

	    List<Map<String, Object>> respuesta = resultados.stream().map(registro -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("bar", registro[0]);
	        map.put("cantidad", registro[1]);
	        return map;
	    }).collect(Collectors.toList());

	    return ResponseEntity.ok(respuesta);
	}
	
	@GetMapping("/stockPorProducto")
	public ResponseEntity<?> obtenerStockPorProducto() {
	    List<Object[]> resultados = stockRepositorio.obtenerStockTotalPorProducto();

	    List<Map<String, Object>> response = resultados.stream().map(obj -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("producto", obj[0]);
	        map.put("cantidad", obj[1]);
	        return map;
	    }).collect(Collectors.toList());

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/pedidosPorMes")
	public ResponseEntity<?> getPedidosPorMes() {
	    List<Object[]> resultados = pedidoRepositorio.countPedidosPorMes();

	    List<Map<String, Object>> response = resultados.stream().map(r -> {
	        Map<String, Object> item = new HashMap<>();
	        item.put("mes", r[0]);
	        item.put("cantidad", r[1]);
	        return item;
	    }).collect(Collectors.toList());

	    return ResponseEntity.ok(response);
	}

	@GetMapping("/topProductos")
	public ResponseEntity<?> getTopProductosPedidos() {
	    List<Object[]> results = detallePedidoRepositorio.findTopProductosMasPedidos();

	    List<Map<String, Object>> datos = results.stream().map(row -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("nombre", row[0]);
	        map.put("cantidad", row[1]);
	        return map;
	    }).collect(Collectors.toList());

	    return ResponseEntity.ok(datos);
	}

	@GetMapping("/exportar")
	public void exportarOds(
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
	    HttpServletResponse response
	) throws Exception {
	    generarOdsService.generarEstadisticasODS(fechaInicio, fechaFin, response);
	}


}
