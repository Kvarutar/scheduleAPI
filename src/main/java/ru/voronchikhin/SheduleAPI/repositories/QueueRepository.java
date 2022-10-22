package ru.voronchikhin.SheduleAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.voronchikhin.SheduleAPI.models.Queue;
import ru.voronchikhin.SheduleAPI.models.QueueKey;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<Queue, QueueKey> {
    List<Queue> findAllById_GroupIdAndId_SubjectIdOrderByEnteredAt(Integer groupId, Integer subjectId);

    Optional<Queue> findAllById_GroupIdAndId_SubjectIdAndId_FullName(Integer groupId, Integer subjectId,
                                                                                 String fullName);

    void deleteById_FullName(String name);
}
