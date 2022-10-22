package ru.voronchikhin.SheduleAPI.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.voronchikhin.SheduleAPI.models.Group;
import ru.voronchikhin.SheduleAPI.services.GroupService;


@Component
public class GroupValidator implements Validator {
    private final GroupService groupService;

    public GroupValidator(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Group.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Group group = (Group) target;

        if (groupService.findByName(group.getName()).isPresent()){
            errors.rejectValue("name", "", "Name should be unique");
        }
    }
}
