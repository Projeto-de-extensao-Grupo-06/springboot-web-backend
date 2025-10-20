package com.solarize.solarizeWebBackend.shared.email;

import com.solarize.solarizeWebBackend.shared.email.model.PasswordRecoveryEmail;
import com.solarize.solarizeWebBackend.shared.exceptions.ServerErrorException;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EmailTemplateProcessor {
    private static String readTemplate (String templateName){
        try {
            InputStream resource = EmailTemplateProcessor.class.getResourceAsStream("template/" + templateName);

            if(resource == null) {
                throw new FileNotFoundException("Template não encontrado: " + templateName);
            }

            String template = new String(resource.readAllBytes(), StandardCharsets.UTF_8);
            resource.close();

            return template;
        } catch (IOException e) {
            throw new ServerErrorException("Internal server error");
        }
    }

    private static String buildTemplate(PasswordRecoveryEmail model) {
        String template = readTemplate("passwordRecoveryTemplate.html");

        return template
                .replace("${name}", model.getName())
                .replace("${code}", model.getCode())
                .replace("${operating_system}", model.getOperatingSystem())
                .replace("${browser_name}", model.getBrowser());
    }
}
