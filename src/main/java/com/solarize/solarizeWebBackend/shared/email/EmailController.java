package com.solarize.solarizeWebBackend.shared.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(
            @RequestBody EmailDto dto
    ) {
        try {
            emailService.sendEmail(dto.getTo(),dto.getSubject(),dto.getBody());
            return ResponseEntity.ok("E-mail enviado com sucesso para: " + dto.getTo());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
