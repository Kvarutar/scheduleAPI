package ru.voronchikhin.SheduleAPI.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.voronchikhin.SheduleAPI.dto.ScheduleDTO;
import ru.voronchikhin.SheduleAPI.dto.SubjectDTO;
import ru.voronchikhin.SheduleAPI.models.Subject;
import ru.voronchikhin.SheduleAPI.services.ScheduleService;
import ru.voronchikhin.SheduleAPI.services.SubjectService;
import ru.voronchikhin.SheduleAPI.util.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;
    private final ModelMapper modelMapper;
    private final SubjectValidator subjectValidator;
    private final ScheduleService scheduleService;

    public SubjectController(SubjectService subjectService, ModelMapper modelMapper, SubjectValidator subjectValidator,
                             ScheduleService scheduleService) {
        this.subjectService = subjectService;
        this.modelMapper = modelMapper;
        this.subjectValidator = subjectValidator;
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<SubjectDTO> all(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "id", required = false) Integer id){

        if (name != null){
            Optional<Subject> subject = subjectService.findByName(name);
            if (subject.isEmpty()){
                throw new SubjectNotFoundException("We can't find subject with this name(");
            }else{
                return Collections.singletonList(convertToSubjectDTO(subject.get()));
            }
        } else if (id != null) {
            Optional<Subject> subject = subjectService.findById(id);
            if (subject.isEmpty()){
                throw new SubjectNotFoundException("We can't find subject with this id(");
            }else{
                return Collections.singletonList(convertToSubjectDTO(subject.get()));
            }
        }else {
            return subjectService.findAll().stream()
                    .map(this::convertToSubjectDTO)
                    .collect(Collectors.toList());
        }
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid SubjectDTO subjectDTO,
                                          BindingResult bindingResult){
        Subject subject = convertToSubject(subjectDTO);
        subjectValidator.validate(subject, bindingResult);

        if(bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);
            throw new SubjectAddingError(errorBuilder.buildError());
        }

        subjectService.save(subject);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        subjectService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Subject convertToSubject(SubjectDTO subjectDTO){
        return modelMapper.map(subjectDTO, Subject.class);
    }

    private SubjectDTO convertToSubjectDTO(Subject subject){
        SubjectDTO subjectDTO = modelMapper.map(subject, SubjectDTO.class);
        List<ScheduleDTO> listDto = new ArrayList<>(subject.getScheduleList()).stream()
                .map(e -> scheduleService.convertToScheduleDTO(e)).collect(Collectors.toList());
        subjectDTO.setScheduleDTOS(listDto);

        return subjectDTO;
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SubjectNotFoundException e){
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SubjectAddingError e){
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
