package com.examples.controler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examples.services.FacturaService;

@RestController
@RequestMapping("/apiFacturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/generarFactura")
    public ResponseEntity<?> generarFactura(
            @RequestParam int proveedorId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin) {

        byte[] pdf = facturaService.generarFactura(proveedorId, inicio, fin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("factura-proveedor-" + proveedorId + ".pdf")
                .build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
