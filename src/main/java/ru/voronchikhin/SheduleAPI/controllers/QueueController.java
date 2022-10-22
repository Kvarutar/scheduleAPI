package ru.voronchikhin.SheduleAPI.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import ru.voronchikhin.SheduleAPI.dto.QueueDTO;
import ru.voronchikhin.SheduleAPI.services.QueueService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/queue")
public class QueueController {
    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @MessageMapping("/add")
    public void sendQueue(@Payload QueueDTO queueDTO){
        queueService.addInQueue(queueDTO);
    }

    @MessageMapping("/delete")
    public void deleteQueue(@Payload QueueDTO queueDTO){
        queueService.deleteInQueue(queueDTO);
    }

    @MessageMapping("/switch")
    public void switchQueue(@Payload QueueDTO queueDTO){
        queueService.switchQueue(queueDTO);
    }

    @MessageMapping("/update")
    public void updateQueue(@Payload QueueDTO queueDTO){
        queueService.updateNote(queueDTO);
    }

    @GetMapping
    public List<QueueDTO> all(@RequestParam(value = "group", required = true) String groupName,
                              @RequestParam(value = "subject", required = true) String subjectName){
        return queueService.findAllByGroupIdAndSubjectId(groupName, subjectName);
    }
}
