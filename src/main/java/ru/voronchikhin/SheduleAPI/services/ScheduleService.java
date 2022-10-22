package ru.voronchikhin.SheduleAPI.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.voronchikhin.SheduleAPI.dto.ScheduleAddDTO;
import ru.voronchikhin.SheduleAPI.dto.ScheduleDTO;
import ru.voronchikhin.SheduleAPI.models.Group;
import ru.voronchikhin.SheduleAPI.models.Schedule;
import ru.voronchikhin.SheduleAPI.models.ScheduleKey;
import ru.voronchikhin.SheduleAPI.models.Subject;
import ru.voronchikhin.SheduleAPI.repositories.ScheduleRepository;
import ru.voronchikhin.SheduleAPI.util.ScheduleAddingException;
import ru.voronchikhin.SheduleAPI.util.ScheduleNotFoundException;
import ru.voronchikhin.SheduleAPI.util.SubjectNotFoundException;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final GroupService groupService;
    private final SubjectService subjectService;

    public ScheduleService(ScheduleRepository scheduleRepository, GroupService groupService,
                           SubjectService subjectService) {
        this.scheduleRepository = scheduleRepository;
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> findById(ScheduleKey scheduleKey){
        return scheduleRepository.findById(scheduleKey);
    }

    public Optional<Schedule> findByGroup(Group group){
        return scheduleRepository.findByGroup(group);
    }

    @Transactional
    public void save(ScheduleDTO scheduleDTO) throws ScheduleAddingException {
        Schedule scheduleToSave = convertToSchedule(scheduleDTO);

        if (scheduleRepository.findById(scheduleToSave.getScheduleKey()).isPresent()){
            throw new ScheduleAddingException("There is already exist schedule with this parameters");
        }

        Group group = scheduleToSave.getGroup();
        Subject subject = scheduleToSave.getSubject();

        scheduleRepository.save(scheduleToSave);
        group.getScheduleList().add(scheduleToSave);
        subject.getScheduleList().add(scheduleToSave);
    }

    @Transactional
    public void delete(ScheduleKey scheduleKey) throws ScheduleNotFoundException{
        Optional<Schedule> scheduleToDelete = scheduleRepository.findById(scheduleKey);

        if (scheduleToDelete.isEmpty()){
            throw new ScheduleNotFoundException("We can't find schedule with this parameters");
        }

        Group group = scheduleToDelete.get().getGroup();
        Subject subject = scheduleToDelete.get().getSubject();
        group.getScheduleList().remove(scheduleToDelete.get());
        subject.getScheduleList().remove(scheduleToDelete.get());

        scheduleRepository.deleteById(scheduleKey);
    }

    @Transactional
    public void update(ScheduleAddDTO scheduleDTO) throws SubjectNotFoundException{
        Schedule oldSchedule = convertToSchedule(scheduleDTO);

        Optional<Schedule> scheduleToUpdate = scheduleRepository.findById(oldSchedule.getScheduleKey());
        Group group = oldSchedule.getGroup();
        Subject subject = oldSchedule.getSubject();
        Optional<Group> newGroup = groupService.findByName(scheduleDTO.getNewGroupName());
        Optional<Subject> newSubject = subjectService.findByName(scheduleDTO.getNewSubjectName());


        if (scheduleToUpdate.isEmpty()){
            throw new SubjectNotFoundException("We can't update subject with this parameters");
        }else{
            scheduleRepository.deleteById(oldSchedule.getScheduleKey());

            group.getScheduleList().remove(scheduleToUpdate.get());
            subject.getScheduleList().remove(scheduleToUpdate.get());

            ScheduleKey newKey = new ScheduleKey(newGroup.get().getId(), newSubject.get().getId(),
                    scheduleDTO.getNewWeekDay(), scheduleDTO.getNewParity());

            Schedule newSchedule = new Schedule(newKey);
            newSchedule.setTime(scheduleDTO.getTime());
            newSchedule.setTeacher(scheduleDTO.getTeacher());
            newSchedule.setRoom(scheduleDTO.getRoom());
            newSchedule.setBuilding(scheduleDTO.getBuilding());
            newSchedule.setGroup(newGroup.get());
            newSchedule.setSubject(newSubject.get());

            scheduleRepository.save(newSchedule);
//            scheduleToUpdate.get().setScheduleKey(newKey);
//            scheduleToUpdate.get().setBuilding(scheduleDTO.getBuilding());
//            scheduleToUpdate.get().setRoom(scheduleDTO.getRoom());
//            scheduleToUpdate.get().setTeacher(scheduleDTO.getTeacher());
//            scheduleToUpdate.get().setTime(scheduleDTO.getTime());

            group.getScheduleList().add(newSchedule);
            subject.getScheduleList().add(newSchedule);
        }
    }

    public Schedule convertToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        Group group = groupService.findByName(scheduleDTO.getGroupName()).get();
        Subject subject = subjectService.findByName(scheduleDTO.getSubjectName()).get();

        ScheduleKey scheduleKey = new ScheduleKey(group.getId(), subject.getId(),
                scheduleDTO.getWeekDay(), scheduleDTO.getParity());
        schedule.setScheduleKey(scheduleKey);

        schedule.setGroup(group);
        schedule.setSubject(subject);
        schedule.setTeacher(scheduleDTO.getTeacher());
        schedule.setBuilding(scheduleDTO.getBuilding());
        schedule.setRoom(scheduleDTO.getRoom());
        schedule.setTime(scheduleDTO.getTime());

        return schedule;
    }

    public ScheduleDTO convertToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setGroupName(schedule.getGroup().getName());
        scheduleDTO.setSubjectName(schedule.getSubject().getName());
        scheduleDTO.setWeekDay(schedule.getScheduleKey().getWeekDay());
        scheduleDTO.setParity(schedule.getScheduleKey().getParity());
        scheduleDTO.setTeacher(schedule.getTeacher());
        scheduleDTO.setBuilding(schedule.getBuilding());
        scheduleDTO.setRoom(schedule.getRoom());
        scheduleDTO.setTime(schedule.getTime());

        return scheduleDTO;
    }
}
