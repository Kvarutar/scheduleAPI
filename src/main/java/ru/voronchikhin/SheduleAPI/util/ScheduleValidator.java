package ru.voronchikhin.SheduleAPI.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.voronchikhin.SheduleAPI.dto.ScheduleDTO;
import ru.voronchikhin.SheduleAPI.models.Schedule;
import ru.voronchikhin.SheduleAPI.services.GroupService;
import ru.voronchikhin.SheduleAPI.services.SubjectService;


@Component
public class ScheduleValidator implements Validator {
    private final GroupService groupService;
    private final SubjectService subjectService;

    public ScheduleValidator(GroupService groupService, SubjectService subjectService) {
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ScheduleDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ScheduleDTO schedule = (ScheduleDTO) target;

        if(groupService.findByName(schedule.getGroupName()).isEmpty()){
            errors.rejectValue("groupName",  "", "Can't find group with this name");
        }
        if (subjectService.findByName(schedule.getSubjectName()).isEmpty()) {
            errors.rejectValue("subjectName",  "", "Can't find subject with this name");
        }
    }
}
