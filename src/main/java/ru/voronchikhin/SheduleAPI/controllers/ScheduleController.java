package ru.voronchikhin.SheduleAPI.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.voronchikhin.SheduleAPI.dto.ScheduleAddDTO;
import ru.voronchikhin.SheduleAPI.dto.ScheduleDTO;
import ru.voronchikhin.SheduleAPI.models.ScheduleKey;
import ru.voronchikhin.SheduleAPI.services.ScheduleService;
import ru.voronchikhin.SheduleAPI.util.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleValidator scheduleValidator;

    public ScheduleController(ScheduleService scheduleService, ScheduleValidator scheduleValidator) {
        this.scheduleService = scheduleService;
        this.scheduleValidator = scheduleValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid ScheduleDTO scheduleDTO,
                                          BindingResult bindingResult){
        scheduleValidator.validate(scheduleDTO, bindingResult);
        if(bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);
            throw new ScheduleAddingException(errorBuilder.buildError());
        }
        scheduleService.save(scheduleDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<HttpStatus> update(@RequestBody ScheduleAddDTO scheduleDTO,
                                             BindingResult bindingResult){
        scheduleValidator.validate(scheduleDTO, bindingResult);
        if(bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);
            throw new ScheduleAddingException(errorBuilder.buildError());
        }

        scheduleService.update(scheduleDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> delete(@RequestBody @Valid ScheduleAddDTO scheduleDTO,
                                             BindingResult bindingResult){
        scheduleValidator.validate(scheduleDTO, bindingResult);
        if(bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);
            throw new ScheduleAddingException(errorBuilder.buildError());
        }
        scheduleService.delete(scheduleService.convertToSchedule(scheduleDTO).getScheduleKey());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler({ScheduleNotFoundException.class, ScheduleAddingException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e){
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
