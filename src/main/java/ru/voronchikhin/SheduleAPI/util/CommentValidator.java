package ru.voronchikhin.SheduleAPI.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.voronchikhin.SheduleAPI.dto.CommentDTO;
import ru.voronchikhin.SheduleAPI.repositories.CommentRepository;
import ru.voronchikhin.SheduleAPI.services.CommentService;
import ru.voronchikhin.SheduleAPI.services.GroupService;
import ru.voronchikhin.SheduleAPI.services.SubjectService;

@Component
public class CommentValidator implements Validator {
    private final GroupService groupService;
    private final SubjectService subjectService;

    public CommentValidator(GroupService groupService, SubjectService subjectService) {
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CommentDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CommentDTO comment = (CommentDTO) target;

        if (groupService.findByName(comment.getGroupName()).isEmpty()){
            errors.rejectValue("groupName", "", "We can't find group with this name");
        }
        if (subjectService.findByName(comment.getSubjectName()).isEmpty()){
            errors.rejectValue("subjectName", "", "We can't find subject with this name");
        }
    }
}
