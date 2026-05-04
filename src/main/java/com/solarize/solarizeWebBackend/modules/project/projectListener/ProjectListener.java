package com.solarize.solarizeWebBackend.modules.project.projectListener;

import com.solarize.solarizeWebBackend.modules.project.projectListener.event.ProjectStatusChangedToRetryingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectListener {
    //rivate final WhatsAppService whatsAppService;

    @EventListener
    private void handleProjectStatusChangedToRetrying(ProjectStatusChangedToRetryingEvent event) {
        System.out.println("Evento disparado");
    }
}
