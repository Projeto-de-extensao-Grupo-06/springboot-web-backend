package com.solarize.solarizeWebBackend.modules.schedule.strategy;

import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.dto.RecipientDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VisitRecipientStrategy implements NotificationRecipientStrategy {

    @Override
    public List<RecipientDTO> resolve(Schedule schedule) {
        List<RecipientDTO> recipients = new ArrayList<>();

        if (schedule.getCoworker() != null) {
            recipients.add(new RecipientDTO(
                    schedule.getCoworker().getEmail(),
                    schedule.getCoworker().getPhone()
            ));
        }

        if (schedule.getProject() != null && schedule.getProject().getClient() != null) {
            recipients.add(new RecipientDTO(
                    schedule.getProject().getClient().getEmail(),
                    schedule.getProject().getClient().getPhone()
            ));
        }

        return recipients;
    }
}