package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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

    List<Schedule> findScheduleByCoworkerAndIsActiveTrue(Coworker coworker);

    List<Schedule> findByCoworkerAndIsActiveTrueAndIdNot(Coworker coworker, Long id);

    List<Schedule> findAllByStartDateBetween(LocalDateTime startDateAfter, LocalDateTime startDateBefore);
}
