package ru.voronchikhin.SheduleAPI.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class SubjectDTO {
    @NotBlank(message = "Name should not be blank")
    private String name;

    private List<ScheduleDTO> scheduleDTOS;

    public SubjectDTO() {
    }

    public SubjectDTO(String name, List<ScheduleDTO> scheduleDTOS) {
        this.name = name;
        this.scheduleDTOS = scheduleDTOS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScheduleDTO> getScheduleDTOS() {
        return scheduleDTOS;
    }

    public void setScheduleDTOS(List<ScheduleDTO> scheduleDTOS) {
        this.scheduleDTOS = scheduleDTOS;
    }
}
