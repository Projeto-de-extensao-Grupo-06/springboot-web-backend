package com.solarize.solarizeWebBackend.modules.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule getFirstByProjectIdOrderByStartDate(Long projectId);
}
