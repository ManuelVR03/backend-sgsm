package com.examples.services;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examples.modelo.Albaran;
import com.examples.modelo.AlbaranFactura;
import com.examples.modelo.Factura;
import com.examples.repositorio.AlbaranFacturaRepositorio;
import com.examples.repositorio.AlbaranRepositorio;
import com.examples.repositorio.FacturaRepositorio;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class FacturaService {

    @Autowired
    private AlbaranRepositorio albaranRepositorio;

    @Autowired
    private FacturaRepositorio facturaRepositorio;

    @Autowired
    private AlbaranFacturaRepositorio albaranFacturaRepositorio;

    @Autowired
    private EmailService emailService;

    public byte[] generarFactura(int proveedorId, Date inicio, Date fin) {
        // 1. Obtener albaranes NO validados del proveedor
        List<Albaran> albaranes = albaranRepositorio.findByPedidoProveedorIdAndFechaGeneracionBetweenAndValidadoFalse(
            proveedorId, inicio, fin
        );

        if (albaranes.isEmpty()) {
            throw new RuntimeException("No hay albaranes pendientes para ese proveedor en ese periodo.");
        }

        // 2. Crear factura
        Factura factura = new Factura();
        factura.setFechaEmision(new Date());
        factura.setProveedor(albaranes.get(0).getPedido().getProveedor());

        BigDecimal total = albaranes.stream()
            .map(Albaran::getImporteTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        factura.setTotalFactura(total);

        factura = facturaRepositorio.save(factura);

        // 3. Relacionar albaranes con factura
        for (Albaran albaran : albaranes) {
            AlbaranFactura af = new AlbaranFactura();
            af.setAlbaran(albaran);
            af.setFactura(factura);
            albaranFacturaRepositorio.save(af);

            albaran.setValidado(true);
            albaranRepositorio.save(albaran); // actualizar albarán como validado
        }

        // 4. Generar PDF (a implementar)
        byte[] pdfBytes = generarPDF(factura, albaranes); // <- método pendiente

        // 5. Enviar email con PDF (a implementar)
        emailService.enviarFacturaProveedor(factura.getProveedor().getEmail(), pdfBytes);

        // 6. Devolver PDF
        return pdfBytes;
    }

    private byte[] generarPDF(Factura factura, List<Albaran> albaranes) {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            document.add(new Paragraph("FACTURA", titleFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Proveedor: " + factura.getProveedor().getNombre()));
            document.add(new Paragraph("CIF: " + factura.getProveedor().getCif()));
            document.add(new Paragraph("Email: " + factura.getProveedor().getEmail()));
            document.add(new Paragraph("Fecha de emisión: " + factura.getFechaEmision()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Stream.of("Nº Albarán", "Fecha", "Importe", "Validado").forEach(header -> {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            });

            for (Albaran a : albaranes) {
                table.addCell(String.valueOf(a.getId()));
                table.addCell(a.getFechaGeneracion().toString());
                table.addCell(a.getImporteTotal().toPlainString() + " €");
                table.addCell(a.isValidado() ? "Sí" : "No");
            }

            document.add(table);

            Paragraph total = new Paragraph("Total Factura: " + factura.getTotalFactura().toPlainString() + " €");
            total.setAlignment(Element.ALIGN_RIGHT);
            total.getFont().setStyle(Font.BOLD);
            document.add(total);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar PDF", e);
        }
    }

}
