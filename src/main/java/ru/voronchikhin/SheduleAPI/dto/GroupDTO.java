package ru.voronchikhin.SheduleAPI.dto;



import ru.voronchikhin.SheduleAPI.models.Schedule;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class GroupDTO {
    @NotBlank(message = "Name should not be blank")
    private String name;

    @NotBlank(message = "Institute name should not be blank")
    private String instituteName;

    private List<ScheduleDTO> scheduleList;

    public GroupDTO() {
    }

    public GroupDTO(String name, String instituteName, List<Schedule> scheduleList) {
        this.name = name;
        this.instituteName = instituteName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public List<ScheduleDTO> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<ScheduleDTO> scheduleList) {
        this.scheduleList = scheduleList;
    }
}
