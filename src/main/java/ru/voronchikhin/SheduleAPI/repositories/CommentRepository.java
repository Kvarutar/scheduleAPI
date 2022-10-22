package ru.voronchikhin.SheduleAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.voronchikhin.SheduleAPI.models.Comment;
import ru.voronchikhin.SheduleAPI.models.CommentKey;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, CommentKey> {
    Optional<Comment> findByGroup_NameAndSubject_NameAndId_CommentDate(String groupName, String subjectName,
                                                                       String commentDate);

    void deleteByGroup_NameAndSubject_NameAndId_CommentDate(String groupName, String subjectName,
                                                            String commentDate);
}
