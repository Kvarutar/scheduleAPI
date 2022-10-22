package ru.voronchikhin.SheduleAPI.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.voronchikhin.SheduleAPI.dto.CommentDTO;
import ru.voronchikhin.SheduleAPI.models.Comment;
import ru.voronchikhin.SheduleAPI.models.CommentKey;
import ru.voronchikhin.SheduleAPI.models.Group;
import ru.voronchikhin.SheduleAPI.models.Subject;
import ru.voronchikhin.SheduleAPI.repositories.CommentRepository;
import ru.voronchikhin.SheduleAPI.util.CommentAddingException;
import ru.voronchikhin.SheduleAPI.util.CommentDeletingException;
import ru.voronchikhin.SheduleAPI.util.CommentUpdateException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final GroupService groupService;
    private final SubjectService subjectService;

    public CommentService(CommentRepository commentRepository, GroupService groupService,
                          SubjectService subjectService) {
        this.commentRepository = commentRepository;
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    public CommentDTO findByGroupNameAndSubjectNameAndCommentDate(String groupName, String subjectName,
                                                                  String commentDate){
        Optional<Comment> comment = commentRepository.findByGroup_NameAndSubject_NameAndId_CommentDate(groupName,
                subjectName, commentDate);

        if (comment.isEmpty()){
            return null;
        }else{
            return convertToCommentDTO(comment.get());
        }
    }

    @Transactional
    public void save(CommentDTO commentDTO) throws CommentAddingException {
        Group group = groupService.findByName(commentDTO.getGroupName()).get();
        Subject subject = subjectService.findByName(commentDTO.getSubjectName()).get();

        if (commentRepository.findByGroup_NameAndSubject_NameAndId_CommentDate(
                commentDTO.getGroupName(), commentDTO.getSubjectName(), commentDTO.getCommentDate()).isPresent()){
            throw new CommentAddingException("There is already exist comment with this parameters");
        }
        commentRepository.save(convertToComment(commentDTO));

        group.getCommentList().add(convertToComment(commentDTO));
        subject.getCommentList().add(convertToComment(commentDTO));
    }

    @Transactional
    public void delete(String groupName, String subjectName, String commentDate) throws CommentDeletingException {
        Group group = groupService.findByName(groupName).get();
        Subject subject = subjectService.findByName(subjectName).get();

        Optional<Comment> comment = commentRepository.findByGroup_NameAndSubject_NameAndId_CommentDate(groupName,
                subjectName, commentDate);

        if (comment.isEmpty()){
            throw new CommentDeletingException("We can't delete comment with this parameters");
        }

        commentRepository.deleteById(comment.get().getId());

        group.getCommentList().remove(comment.get());
        subject.getCommentList().remove(comment.get());
    }

    @Transactional
    public void update(CommentDTO commentDTO) throws CommentUpdateException {
        Group group = groupService.findByName(commentDTO.getGroupName()).get();
        Subject subject = subjectService.findByName(commentDTO.getSubjectName()).get();

        Optional<Comment> comment = commentRepository.findByGroup_NameAndSubject_NameAndId_CommentDate(
                commentDTO.getGroupName(), commentDTO.getSubjectName(), commentDTO.getCommentDate());

        if (comment.isEmpty()){
            throw new CommentUpdateException("We can't find group with this parameters");
        }

        group.getCommentList().remove(comment.get());
        subject.getCommentList().remove(comment.get());

        comment.get().setNote(commentDTO.getNote());

        group.getCommentList().add(comment.get());
        subject.getCommentList().add(comment.get());
    }

    private CommentDTO convertToCommentDTO(Comment comment){
        return new CommentDTO(comment.getGroup().getName(), comment.getSubject().getName(),
                comment.getId().getCommentDate(), comment.getNote());
    }

    private Comment convertToComment(CommentDTO commentDTO){
        Group group = groupService.findByName(commentDTO.getGroupName()).get();
        Subject subject = subjectService.findByName(commentDTO.getSubjectName()).get();
        CommentKey commentKey = new CommentKey(group.getId(), subject.getId(), commentDTO.getCommentDate());
        Comment comment = new Comment(commentKey);
        comment.setNote(commentDTO.getNote());
        comment.setGroup(group);
        comment.setSubject(subject);
        //здесь можно проверить
        return comment;
    }
}
