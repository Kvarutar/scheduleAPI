package ru.voronchikhin.SheduleAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.voronchikhin.SheduleAPI.models.Group;


import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Optional<Group> findByName(String name);
    void deleteByName(String name);

}
