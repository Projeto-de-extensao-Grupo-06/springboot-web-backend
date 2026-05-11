package com.solarize.solarizeWebBackend.modules.schedule.strategy;

import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.dto.RecipientDTO;

import java.util.List;

public interface NotificationRecipientStrategy {
    List<RecipientDTO> resolve(Schedule schedule);
}
