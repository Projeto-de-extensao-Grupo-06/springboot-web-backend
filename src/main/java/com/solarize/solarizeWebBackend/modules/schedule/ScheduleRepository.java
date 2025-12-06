package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    List<Schedule> findAllByStartDateBetweenAndIsActiveTrue(LocalDateTime startDateAfter, LocalDateTime startDateBefore);

    Optional<Schedule> findByIdAndIsActiveTrue(Long id);

    List<Schedule> findAllByProject(Project project);
}
