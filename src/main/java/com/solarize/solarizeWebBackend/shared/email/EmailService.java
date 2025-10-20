package com.solarize.solarizeWebBackend.shared.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws IOException, MessagingException {

        MimeMessage message = mailSender.createMimeMessage();


        message.setFrom(new InternetAddress(System.getenv("EMAIL")));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);


        htmlTemplate = htmlTemplate.replace("${mensagem}",body);
        message.setContent(htmlTemplate, "text/html; charset=utf-8");


        mailSender.send(message);
    }

}
