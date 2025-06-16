package com.examples.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarFacturaProveedor(String destinatario, byte[] pdfAdjunto) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo("manuel.verdejo.ramirez.alu@iesjulioverne.es");
            helper.setSubject("Factura mensual - Segunda Giralda");
            helper.setText("Adjuntamos su factura mensual correspondiente. Gracias por su colaboración.");
            helper.addAttachment("factura.pdf", new ByteArrayResource(pdfAdjunto));

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar correo", e);
        }
    }
}
