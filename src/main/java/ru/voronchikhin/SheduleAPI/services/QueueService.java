package ru.voronchikhin.SheduleAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.voronchikhin.SheduleAPI.dto.QueueDTO;
import ru.voronchikhin.SheduleAPI.models.Group;
import ru.voronchikhin.SheduleAPI.models.Queue;
import ru.voronchikhin.SheduleAPI.models.QueueKey;
import ru.voronchikhin.SheduleAPI.models.Subject;
import ru.voronchikhin.SheduleAPI.repositories.QueueRepository;
import ru.voronchikhin.SheduleAPI.util.QueueNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class QueueService {
    private final QueueRepository queueRepository;
    private final GroupService groupService;
    private final SubjectService subjectService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public QueueService(QueueRepository queueRepository, GroupService groupService, SubjectService subjectService) {
        this.queueRepository = queueRepository;
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    public List<QueueDTO> all(){
        List<Queue> queues = queueRepository.findAll();
        return queues.stream().map(this::convertToQueueDTO).collect(Collectors.toList());
    }

    public Optional<Queue> findByGroupIdAndSubjectIdAndFullName(int groupId, int subjectId, String fullName){
        return queueRepository.findAllById_GroupIdAndId_SubjectIdAndId_FullName(groupId,
                subjectId, fullName);
    }

    public List<QueueDTO> findAllByGroupIdAndSubjectId(String groupName, String subjectName)
            throws QueueNotFoundException {

        Optional<Group> group = groupService.findByName(groupName);
        Optional<Subject> subject = subjectService.findByName(subjectName);

        if (group.isEmpty() || subject.isEmpty()){
            throw new QueueNotFoundException("We can't find queue for this group or/and this subject");
        }

        return queueRepository.findAllById_GroupIdAndId_SubjectIdOrderByEnteredAt(group.get().getId(),
                        subject.get().getId())
                .stream()
                .map(this::convertToQueueDTO).collect(Collectors.toList());
    }

    @Transactional
    public void addInQueue(QueueDTO queueDTO){
        Queue queueToSave = convertToQueue(queueDTO);

        Optional<Group> group = groupService.findByName(queueToSave.getGroup().getName());
        Optional<Subject> subject = subjectService.findByName(queueToSave.getSubject().getName());

        if(group.isEmpty() || subject.isEmpty()){
            throw new QueueNotFoundException("We can't find queue with this group name and/or subject name");
        }

        queueRepository.save(queueToSave);
        List<Queue> queueList = queueRepository.findAllById_GroupIdAndId_SubjectIdOrderByEnteredAt(group.get().getId(),
                subject.get().getId());

        queueToSave.getGroup().getQueueList().add(queueToSave);
        queueToSave.getSubject().getQueueList().add(queueToSave);

        //group.get().getQueueList().add(queueToSave);
        //subject.get().getQueueList().add(queueToSave);

        List<QueueDTO> queueDTOS = queueList.stream().map(this::convertToQueueDTO).collect(Collectors.toList());

        simpMessagingTemplate.convertAndSend("/list/", queueDTOS);
    }

    @Transactional
    public void deleteInQueue(QueueDTO queueDTO) {
        Optional<Group> group = groupService.findByName(queueDTO.getGroupName());
        Optional<Subject> subject = subjectService.findByName(queueDTO.getSubjectName());

        if (group.isEmpty() || subject.isEmpty()){
            throw new QueueNotFoundException("We can't find queue with this group name and/or subject name");
        }else{
            Queue queueToDelete = findByGroupIdAndSubjectIdAndFullName(group.get().getId(), subject.get().getId(),
                    queueDTO.getFullName()).get();

            List<Queue> groupQueues = queueToDelete.getGroup().getQueueList();
            List<Queue> subjectsQueues = queueToDelete.getSubject().getQueueList();

            String elementToDelete = queueToDelete.getId().getFullName();

            //queueRepository.deleteById_FullName(elementToDelete);
            queueRepository.deleteById(queueToDelete.getId());
            //groupQueues.removeIf(queue -> Objects.equals(queue.getId().getFullName(), elementToDelete));
            //subjectsQueues.removeIf(queue -> Objects.equals(queue.getId().getFullName(), elementToDelete));
            groupQueues.remove(queueToDelete);
            subjectsQueues.remove(queueToDelete);

            List<Queue> queueList = queueRepository.findAllById_GroupIdAndId_SubjectIdOrderByEnteredAt(
                    group.get().getId(), subject.get().getId());

            List<QueueDTO> queueDTOS = queueList.stream().map(this::convertToQueueDTO).collect(Collectors.toList());
            simpMessagingTemplate.convertAndSend("/list/", queueDTOS);
        }
    }

    @Transactional
    public void switchQueue(QueueDTO queueDTO){
        Optional<Group> group = groupService.findByName(queueDTO.getGroupName());
        Optional<Subject> subject = subjectService.findByName(queueDTO.getSubjectName());

        if (group.isEmpty() || subject.isEmpty()){
            throw new QueueNotFoundException("We can't find queue with this group name and/or subject name");
        }else {
            List<Queue> groupQueues = getGroupQueueListWhereSubject(group.get(), subject.get());
            //List<Queue> subjectQueues = getGroupQueueListWhereSubject(group.get(), subject.get());

            Queue queueToUpdate = findByGroupIdAndSubjectIdAndFullName(group.get().getId(), subject.get().getId(),
                    queueDTO.getFullName()).get();
            Queue queueToUpdate2 = findByGroupIdAndSubjectIdAndFullName(group.get().getId(), subject.get().getId(),
                    queueDTO.getSwitcherFullName()).get();

            Date temp = queueToUpdate.getEnteredAt();
            groupQueues.get(groupQueues.indexOf(queueToUpdate)).setEnteredAt(queueToUpdate2.getEnteredAt());
            groupQueues.get(groupQueues.indexOf(queueToUpdate2)).setEnteredAt(temp);

            List<Queue> queueList = queueRepository.findAllById_GroupIdAndId_SubjectIdOrderByEnteredAt(
                    group.get().getId(), subject.get().getId());

            List<QueueDTO> queueDTOS = queueList.stream().map(this::convertToQueueDTO).collect(Collectors.toList());
            simpMessagingTemplate.convertAndSend("/list/", queueDTOS);
        }
    }

    @Transactional
    public void updateNote(QueueDTO queueDTO){
        Optional<Group> group = groupService.findByName(queueDTO.getGroupName());
        Optional<Subject> subject = subjectService.findByName(queueDTO.getSubjectName());

        if (group.isEmpty() || subject.isEmpty()){
            throw new QueueNotFoundException("We can't find queue with this group name and/or subject name");
        }else {
            //List<Queue> groupQueues = getGroupQueueListWhereSubject(group.get(), subject.get());
            Queue queueToUpdate = findByGroupIdAndSubjectIdAndFullName(group.get().getId(), subject.get().getId(),
                    queueDTO.getFullName()).get();

            List<Queue> groupQueues = queueToUpdate.getGroup().getQueueList();

            groupQueues.get(groupQueues.indexOf(queueToUpdate)).setNote(queueDTO.getNote());

            List<Queue> queueList = queueRepository.findAllById_GroupIdAndId_SubjectIdOrderByEnteredAt(
                    group.get().getId(), subject.get().getId());

            List<QueueDTO> queueDTOS = queueList.stream().map(this::convertToQueueDTO).collect(Collectors.toList());
            simpMessagingTemplate.convertAndSend("/list/", queueDTOS);
        }
    }

    private Queue convertToQueue(QueueDTO queueDTO){
        Queue queue = new Queue();
        Subject subject = subjectService.findByName(queueDTO.getSubjectName()).get();
        Group group = groupService.findByName(queueDTO.getGroupName()).get();

        QueueKey queueKey = new QueueKey(group.getId(), subject.getId(), queueDTO.getFullName());

        queue.setId(queueKey);
        queue.setEnteredAt(new Date());
        queue.setNote(queueDTO.getNote());
        queue.setGroup(group);
        queue.setSubject(subject);

        return queue;
    }

    private QueueDTO convertToQueueDTO(Queue queue){
        QueueDTO queueDTO = new QueueDTO(queue.getId().getFullName(), queue.getNote(), queue.getGroup().getName(),
                queue.getSubject().getName());

        return queueDTO;
    }

    private List<Queue> getGroupQueueListWhereSubject(Group group, Subject subject){
        return group.getQueueList().stream().filter(
                queue -> queue.getId().getSubjectId() == subject.getId()).collect(Collectors.toList());
    }

    private List<Queue> getSubjectQueueListWhereGroup(Group group, Subject subject){
        return subject.getQueueList().stream().filter(
                queue -> queue.getId().getGroupId() == group.getId()).collect(Collectors.toList());
    }
}
