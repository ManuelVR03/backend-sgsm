package com.examples.controler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.examples.DTO.AlbaranDTO;
import com.examples.DTO.AlbaranDTOConverter;
import com.examples.modelo.Albaran;
import com.examples.repositorio.AlbaranRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiAlbaranes")
public class AlbaranController {

    @Autowired
    private AlbaranRepositorio albaranRepositorio;

    @Autowired
    private AlbaranDTOConverter albaranDTOConverter;
    
    @GetMapping("/todos")
    public List<AlbaranDTO> getTodosDTO() {
        List<Albaran> albaranes = albaranRepositorio.findAll();
        return albaranes.stream()
            .map(albaranDTOConverter::convertirADto)
            .collect(Collectors.toList());
    }

    @GetMapping("/porProveedor")
    public List<AlbaranDTO> getAlbaranesPorProveedor(
        @RequestParam("inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
        @RequestParam("fin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin,
        @RequestParam("proveedorId") int proveedorId
    ) {
        List<Albaran> albaranes = albaranRepositorio.findByPedidoProveedorIdAndFechaGeneracionBetweenAndValidadoFalse(
            proveedorId, inicio, fin
        );

        return albaranes.stream()
                .map(albaranDTOConverter::convertirADto)
                .collect(Collectors.toList());
    }
}
