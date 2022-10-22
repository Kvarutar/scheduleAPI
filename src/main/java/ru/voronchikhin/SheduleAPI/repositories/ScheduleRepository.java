package ru.voronchikhin.SheduleAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.voronchikhin.SheduleAPI.models.Group;
import ru.voronchikhin.SheduleAPI.models.Schedule;
import ru.voronchikhin.SheduleAPI.models.ScheduleKey;


import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleKey> {
    Optional<Schedule> findByGroup(Group group);
}
