package com.saborperuano.gestionrestaurante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String nombre, String correo, String mensaje) {

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("info.saborperuano2025@gmail.com");   // correo que recibirá los mensajes
        email.setSubject("Nuevo mensaje de contacto");

        email.setText(
                "Has recibido un nuevo mensaje desde el formulario de contacto:\n\n" +
                "Nombre: " + nombre + "\n" +
                "Correo: " + correo + "\n\n" +
                "Mensaje:\n" + mensaje
        );

        email.setReplyTo(correo);

        mailSender.send(email);
    }
}