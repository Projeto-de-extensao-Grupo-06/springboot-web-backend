package com.solarize.solarizeWebBackend.shared.communication.whatsApp;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {
    @Async
    public void sendMessage(String to, String message) {}
}
