package com.solarize.solarizeWebBackend.modules.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("""
        SELECT MIN(s.startDate)
        FROM Schedule s
        WHERE s.project.id = :projectId
          AND s.startDate >= CURRENT_TIMESTAMP
          AND s.isActive = true
    """)
    LocalDateTime findNextScheduleByProjectId(@Param("projectId") Long projectId);
}
