package com.solarize.solarizeWebBackend.shared.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MailTemplateReader {
    public static String read (String templateName){
        try {
            ClassPathResource resource = new ClassPathResource("templates/teste.html");
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    } 
}
