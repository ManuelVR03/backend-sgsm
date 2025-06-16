package com.examples.services;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.examples.repositorio.GenerarOdsRepositorio;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class GenerarOdsService {

    @Autowired
    private GenerarOdsRepositorio generarOdsRepositorio;

    public void generarEstadisticasODS(Date fechaInicio, Date fechaFin, HttpServletResponse response) throws Exception {
        // Cargar plantilla ODS desde resources
        ClassPathResource plantilla = new ClassPathResource("docs/estadisticas.ods");
        File archivoTemp = File.createTempFile("estadisticas-", ".ods");
        try (InputStream in = plantilla.getInputStream()) {
            Files.copy(in, archivoTemp.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        SpreadSheet spreadSheet = SpreadSheet.createFromFile(archivoTemp);
        Sheet hoja = spreadSheet.getSheet(0);

        List<Object[]> resultados = this.generarOdsRepositorio.contarPedidosPorBar(fechaInicio, fechaFin);

        String estiloColumnaBar = hoja.getRowCount() > 3 ? hoja.getCellAt(1, 3).getStyleName() : null;
        String estiloColumnaCantidad = hoja.getRowCount() > 3 ? hoja.getCellAt(2, 3).getStyleName() : null;

        int fila = 3;
        for (Object[] registro : resultados) {
            hoja.ensureRowCount(fila + 1);

            if (estiloColumnaBar != null)
                hoja.getCellAt(1, fila).setStyleName(estiloColumnaBar);
            if (estiloColumnaCantidad != null)
                hoja.getCellAt(2, fila).setStyleName(estiloColumnaCantidad);

            hoja.setValueAt(registro[0], 1, fila);
            hoja.setValueAt(registro[1], 2, fila);
            fila++;
        }
        
        List<Object[]> resultados2 = this.generarOdsRepositorio.contarPedidosPorProveedor(fechaInicio, fechaFin);

        String estiloColumnaProveedor = hoja.getRowCount() > 3 ? hoja.getCellAt(5, 3).getStyleName() : null;
        String estiloColumnaCantidad2 = hoja.getRowCount() > 3 ? hoja.getCellAt(6, 3).getStyleName() : null;

        fila = 3;
        for (Object[] registro : resultados2) {
            hoja.ensureRowCount(fila + 1);

            if (estiloColumnaProveedor != null)
                hoja.getCellAt(5, fila).setStyleName(estiloColumnaProveedor);
            if (estiloColumnaCantidad2 != null)
                hoja.getCellAt(6, fila).setStyleName(estiloColumnaCantidad2);

            hoja.setValueAt(registro[0], 5, fila);
            hoja.setValueAt(registro[1], 6, fila);
            fila++;
        }

        File ficheroSalida = File.createTempFile("estadisticas-final-", ".ods");
        spreadSheet.saveAs(ficheroSalida);

        response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
        response.setHeader("Content-Disposition", "attachment; filename=estadisticas.ods");
        Files.copy(ficheroSalida.toPath(), response.getOutputStream());

        ficheroSalida.delete();
        archivoTemp.delete();
    }
}
