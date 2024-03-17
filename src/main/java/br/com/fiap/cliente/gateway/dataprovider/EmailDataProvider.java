package br.com.fiap.cliente.gateway.dataprovider;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailDataProvider {
    private final JavaMailSender emailSender;

    public EmailDataProvider(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void enviarEmail(
            String destinatario, String titulo, String mensagem) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pos-tech@lanchonete.com");
        message.setTo(destinatario);
        message.setSubject(titulo);
        message.setText(mensagem);
        emailSender.send(message);

    }
}
