package ru.voronchikhin.SheduleAPI.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ScheduleDTO {

    @NotBlank(message = "Group name should not be blank")
    private String groupName;

    @NotBlank(message = "Subject name should not be blank")
    private String subjectName;

    @NotBlank(message = "Week day should not be blank")
    private String weekDay;

    @NotNull(message = "Parity should not be empty")
    private Boolean parity;

    private String teacher;


    private String building;


    private String room;


    private String time;

    public ScheduleDTO() {
    }

    public ScheduleDTO(String groupName, String subjectName, String weekDay, Boolean parity, String teacher,
                       String building, String room, String time) {
        this.groupName = groupName;
        this.subjectName = subjectName;
        this.weekDay = weekDay;
        this.parity = parity;
        this.teacher = teacher;
        this.building = building;
        this.room = room;
        this.time = time;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public Boolean getParity() {
        return parity;
    }

    public void setParity(Boolean parity) {
        this.parity = parity;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
