package com.solarize.solarizeWebBackend.modules.project.projectListener;

import com.solarize.solarizeWebBackend.modules.project.projectListener.event.ProjectStatusChangedToRetryingEvent;
import com.solarize.solarizeWebBackend.shared.communication.whatsApp.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProjectListener {
    private final WhatsAppService whatsAppService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void handleProjectStatusChangedToRetrying(ProjectStatusChangedToRetryingEvent event) {
        System.out.println("Evento disparado");
    }
}
