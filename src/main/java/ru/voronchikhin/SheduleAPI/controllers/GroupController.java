package ru.voronchikhin.SheduleAPI.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.voronchikhin.SheduleAPI.dto.GroupDTO;
import ru.voronchikhin.SheduleAPI.dto.ScheduleDTO;
import ru.voronchikhin.SheduleAPI.models.Group;
import ru.voronchikhin.SheduleAPI.services.GroupService;
import ru.voronchikhin.SheduleAPI.services.ScheduleService;
import ru.voronchikhin.SheduleAPI.util.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;
    private final ModelMapper modelMapper;
    private final GroupValidator groupValidator;
    private final ScheduleService scheduleService;

    public GroupController(GroupService groupService, ModelMapper modelMapper, GroupValidator groupValidator,
                           ScheduleService scheduleService) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
        this.groupValidator = groupValidator;
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<GroupDTO> all(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "id", required = false) Integer id){
        if(name != null){
            Optional<Group> group = groupService.findByName(name);
            if (group.isEmpty()){
                throw new GroupNotFoundException("We can't find group with this name(");
            }else{
                return Collections.singletonList(convertToGroupDTO(group.get()));
            }
        } else if (id != null) {
            Optional<Group> group = groupService.findById(id);
            if (group.isEmpty()){
                throw new GroupNotFoundException("We can't find group with this id(");
            }else{
                return Collections.singletonList(convertToGroupDTO(group.get()));
            }
        }else{
            return groupService.findAll().stream()
                    .map(this::convertToGroupDTO)
                    .collect(Collectors.toList());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid GroupDTO groupDTO,
                                          BindingResult bindingResult){
        Group groupToAdd = convertToGroup(groupDTO);
        groupValidator.validate(groupToAdd, bindingResult);
        if (bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);

            throw new GroupAddingException(errorBuilder.buildError());
        }

        groupService.save(groupToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{name}/delete")
    public ResponseEntity<HttpStatus> delete(@PathVariable("name") String name){
        groupService.delete(name);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{name}/update")
    public ResponseEntity<HttpStatus> update(@PathVariable("name") String name, @RequestBody GroupDTO groupDTO,
                                             BindingResult bindingResult){

        if (groupService.findByName(name).isEmpty()){
            throw new GroupNotFoundException("We can't find group with this name(");
        }
        if (bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);

            throw new GroupAddingException(errorBuilder.buildError());
        }

        groupService.update(groupService.findByName(name).get().getId(), groupDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    private Group convertToGroup(GroupDTO groupDTO){
        return modelMapper.map(groupDTO, Group.class);
    }

    private GroupDTO convertToGroupDTO(Group group){
        GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
        List<ScheduleDTO> listDto = new ArrayList<>(group.getScheduleList()).stream()
                .map(scheduleService::convertToScheduleDTO).collect(Collectors.toList());
        groupDTO.setScheduleList(listDto);

        return groupDTO;
    }

    @ExceptionHandler({GroupAddingException.class, GroupNotFoundException.class})
    private ResponseEntity<ErrorResponse> handleException(RuntimeException e){
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler
//    private ResponseEntity<ErrorResponse> handleException(GroupAddingException e){
//        ErrorResponse response = new ErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
}
