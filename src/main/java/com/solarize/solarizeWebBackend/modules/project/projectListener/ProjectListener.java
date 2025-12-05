package com.solarize.solarizeWebBackend.modules.project.projectListener;

import com.solarize.solarizeWebBackend.modules.project.projectListener.event.ProjectStatusChangedToRetryingEvent;
import com.solarize.solarizeWebBackend.shared.communication.whatsApp.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectListener {
    private final WhatsAppService whatsAppService;

    @EventListener
    private void handleProjectStatusChangedToRetrying(ProjectStatusChangedToRetryingEvent event) {
        System.out.println("Evento disparado");
    }
}
