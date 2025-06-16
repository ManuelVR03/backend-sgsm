package com.examples.DTO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.examples.modelo.Albaran;
import com.examples.modelo.Bar;
import com.examples.modelo.DetallePedido;
import com.examples.modelo.Pedido;
import com.examples.modelo.Producto;
import com.examples.modelo.Proveedor;

@Component
public class AlbaranDTOConverter {

    public AlbaranDTO convertirADto(Albaran albaran) {
        AlbaranDTO dto = new AlbaranDTO();

        dto.setId(albaran.getId());
        dto.setFechaGeneracion(albaran.getFechaGeneracion());
        dto.setImporteTotal(albaran.getImporteTotal());
        dto.setValidado(albaran.isValidado());

        Pedido pedido = albaran.getPedido();
        if (pedido != null) {
            dto.setPedidoId(pedido.getId());

            Proveedor proveedor = pedido.getProveedor();
            if (proveedor != null) {
                dto.setProveedorId(proveedor.getId());
                dto.setProveedorNombre(proveedor.getNombre());
                dto.setProveedorCif(proveedor.getCif());
                dto.setProveedorDireccion(proveedor.getDireccion());
                dto.setProveedorTelefono(proveedor.getTelefono());
                dto.setProveedorEmail(proveedor.getEmail());
            }

            Bar bar = pedido.getBar();
            if (bar != null) {
                dto.setClienteNombre(bar.getNombre());
                dto.setClienteDireccion(bar.getDireccion());
                dto.setClienteNif(bar.getCif());
            }

            List<DetallePedido> detalles = pedido.getDetallePedidos();
            if (detalles != null) {
                List<AlbaranProductoDTO> productos = detalles.stream()
                    .map(dp -> {
                        AlbaranProductoDTO p = new AlbaranProductoDTO();

                        Producto prod = dp.getProducto();
                        p.setReferencia(prod != null ? String.valueOf(prod.getId()) : "N/A");
                        p.setDescripcion(prod != null ? prod.getNombre() : "Desconocido");
                        p.setCantidad(dp.getCantidad());
                        p.setPrecio(prod != null ? prod.getPrecioUnitario() : null);

                        return p;
                    })
                    .collect(Collectors.toList());

                dto.setProductos(productos);
            }
        }

        return dto;
    }
}
