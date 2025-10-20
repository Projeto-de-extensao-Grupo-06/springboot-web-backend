package com.solarize.solarizeWebBackend.shared.utils;

import com.solarize.solarizeWebBackend.shared.exceptions.ServerErrorException;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MailTemplateReader {
    public static String read (String templateName){
        try {
            ClassPathResource resource = new ClassPathResource("templates/" + templateName);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ServerErrorException("Internal server error");
        }


    }
}
