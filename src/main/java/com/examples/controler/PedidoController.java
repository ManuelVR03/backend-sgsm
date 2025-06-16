package com.examples.controler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.DTO.DetalleProductoDTO;
import com.examples.DTO.NuevoPedidoDTO;
import com.examples.DTO.PedidoDTO;
import com.examples.DTO.PedidoDTOConverter;
import com.examples.DTO.ProductoDTO;
import com.examples.modelo.Albaran;
import com.examples.modelo.DetallePedido;
import com.examples.modelo.EstadoPedido;
import com.examples.modelo.Pedido;
import com.examples.modelo.Producto;
import com.examples.modelo.Stock;
import com.examples.repositorio.AlbaranRepositorio;
import com.examples.repositorio.BarRepositorio;
import com.examples.repositorio.DetallePedidoRepositorio;
import com.examples.repositorio.EstadoPedidoRepositorio;
import com.examples.repositorio.PedidoRepositorio;
import com.examples.repositorio.ProductoRepositorio;
import com.examples.repositorio.ProveedorRepositorio;
import com.examples.repositorio.StockRepositorio;
import com.examples.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apiPedidos")
public class PedidoController {

	@Autowired
	private PedidoRepositorio pedidoRepositorio;

	@Autowired
	private PedidoDTOConverter pedidoDTOConverter;

	@Autowired
	private StockRepositorio stockRepositorio;

	@Autowired
	private EstadoPedidoRepositorio estadoPedidoRepositorio;

	@Autowired
	private DetallePedidoRepositorio detallePedidoRepositorio;

	@Autowired
	private BarRepositorio barRepositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private ProveedorRepositorio proveedorRepositorio;

	@Autowired
	private ProductoRepositorio productoRepositorio;

	@Autowired
	private AlbaranRepositorio albaranRepositorio;

	@GetMapping("/todos")
	public ResponseEntity<?> getTodosLosPedidos() {
		List<Pedido> pedidos = pedidoRepositorio.findAll();
		List<PedidoDTO> dtoList = pedidos.stream().map(pedidoDTOConverter::convertirADto).collect(Collectors.toList());
		return ResponseEntity.ok(dtoList);
	}

	@GetMapping("/bar/{barId}")
	public ResponseEntity<?> getPedidosPorBar(@PathVariable int barId) {
		List<Pedido> pedidos = pedidoRepositorio.findByBarId(barId);
		List<PedidoDTO> dtoList = pedidos.stream().map(pedidoDTOConverter::convertirADto).collect(Collectors.toList());
		return ResponseEntity.ok(dtoList);
	}

	@PutMapping("/recibido/{pedidoId}")
	public ResponseEntity<?> marcarPedidoComoRecibido(@PathVariable int pedidoId) {
		Optional<Pedido> optPedido = pedidoRepositorio.findById(pedidoId);

		if (optPedido.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap("mensaje", "Pedido no encontrado."));
		}

		Pedido pedido = optPedido.get();

		if ("Entregado".equalsIgnoreCase(pedido.getEstadoPedido().getNombre())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Collections.singletonMap("mensaje", "El pedido ya está entregado."));
		}

		// Cambiar estado a "Entregado"
		EstadoPedido entregado = estadoPedidoRepositorio.findByNombre("Entregado");
		pedido.setEstadoPedido(entregado);
		pedido.setFechaRecepcion(new Date());
		pedidoRepositorio.save(pedido);

		// Obtener detalles del pedido
		List<DetallePedido> detalles = detallePedidoRepositorio.findByPedidoId(pedidoId);

		// Actualizar stock
		for (DetallePedido detalle : detalles) {
			int barId = pedido.getBar().getId();
			int productoId = detalle.getProducto().getId();

			Stock stock = stockRepositorio.findByBarIdAndProductoId(barId, productoId).orElseGet(() -> {
				Stock nuevo = new Stock();
				nuevo.setBar(pedido.getBar());
				nuevo.setProducto(detalle.getProducto());
				nuevo.setCantidad(BigDecimal.ZERO);
				return nuevo;
			});

			BigDecimal nuevaCantidad = stock.getCantidad().add(detalle.getCantidad());
			stock.setCantidad(nuevaCantidad);
			stockRepositorio.save(stock);
		}

		// Generar albarán tras recibir el pedido
		Albaran albaran = new Albaran();
		albaran.setPedido(pedido);
		albaran.setFechaGeneracion(new Date());
		albaran.setValidado(false); // aún no asociado a factura

		// Opcional: calcular importe total si se requiere (basado en los detalles)
		BigDecimal total = detalles.stream().map(d -> d.getCantidad().multiply(d.getProducto().getPrecioUnitario()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		albaran.setImporteTotal(total);
		albaranRepositorio.save(albaran);

		return ResponseEntity
				.ok(Collections.singletonMap("mensaje", "Pedido marcado como recibido, stock actualizado y albarán generado."));
	}

	@PostMapping("/crearPedido")
	@Transactional
	public ResponseEntity<?> crearPedidoCompleto(@RequestBody NuevoPedidoDTO dto) {
		Pedido pedido = new Pedido();
		pedido.setBar(barRepositorio.findById(dto.getBarId())
				.orElseThrow(() -> new RuntimeException("Bar no encontrado con ID: " + dto.getBarId())));
		pedido.setUsuario(usuarioRepositorio.findById(dto.getUsuarioId())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId())));
		pedido.setProveedor(proveedorRepositorio.findById(dto.getProveedorId())
				.orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + dto.getProveedorId())));

		EstadoPedido estado = estadoPedidoRepositorio.findByNombre("En camino");
		pedido.setEstadoPedido(estado);
		pedido.setFechaRealizacion(new Date());

		// 💾 Guardar primero el pedido y obtener su ID generado
		Pedido pedidoGuardado = pedidoRepositorio.save(pedido);

		// ✅ Insertar los detalles después, referenciando al pedido guardado
		for (DetalleProductoDTO detalleDto : dto.getProductos()) {
			DetallePedido detalle = new DetallePedido();
			detalle.setPedido(pedidoGuardado);
			detalle.setProducto(productoRepositorio.findById(detalleDto.getProductoId()).orElseThrow(
					() -> new RuntimeException("Producto no encontrado con ID: " + detalleDto.getProductoId())));
			detalle.setCantidad(detalleDto.getCantidad());
			detallePedidoRepositorio.save(detalle);
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(Collections.singletonMap("mensaje", "Pedido creado correctamente."));
	}
}
