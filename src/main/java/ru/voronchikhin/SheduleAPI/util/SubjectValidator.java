package ru.voronchikhin.SheduleAPI.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.voronchikhin.SheduleAPI.models.Subject;
import ru.voronchikhin.SheduleAPI.services.SubjectService;


@Component
public class SubjectValidator implements Validator {
    private final SubjectService subjectService;

    public SubjectValidator(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Subject.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Subject subject = (Subject) target;

        if (subjectService.findByName(subject.getName()).isPresent()){
            errors.rejectValue("name", "", "Name should be unique");
        }
    }
}
