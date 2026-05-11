package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.schedule.strategy.NoteRecipientStrategy;
import com.solarize.solarizeWebBackend.modules.schedule.strategy.NotificationRecipientStrategy;
import com.solarize.solarizeWebBackend.modules.schedule.strategy.VisitRecipientStrategy;

public enum ScheduleTypeEnum {
    TECHNICAL_VISIT(new VisitRecipientStrategy()),
    INSTALL_VISIT(new VisitRecipientStrategy()),
    NOTE(new NoteRecipientStrategy());

    public final NotificationRecipientStrategy recipientStrategy;

    ScheduleTypeEnum(NotificationRecipientStrategy recipientStrategy) {
        this.recipientStrategy = recipientStrategy;
    }
}
