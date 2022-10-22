package ru.voronchikhin.SheduleAPI.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ScheduleAddDTO extends ScheduleDTO{
    @NotBlank(message = "Group name should not be blank")
    private String groupName;

    @NotBlank(message = "Subject name should not be blank")
    private String subjectName;

    @NotBlank(message = "Week day should not be blank")
    private String weekDay;

    @NotNull(message = "Parity should not be empty")
    private Boolean parity;

    private String newGroupName;

    private String newSubjectName;

    private String newWeekDay;

    private Boolean newParity;


    private String teacher;


    private String building;


    private String room;


    private String time;

    public ScheduleAddDTO() {
    }

    public ScheduleAddDTO(String groupName, String subjectName, String weekDay, Boolean parity, String newGroupName,
                       String newSubjectName, String newWeekDay, Boolean newParity, String teacher, String building,
                       String room, String time) {
        this.groupName = groupName;
        this.subjectName = subjectName;
        this.weekDay = weekDay;
        this.parity = parity;
        this.newGroupName = newGroupName;
        this.newSubjectName = newSubjectName;
        this.newWeekDay = newWeekDay;
        this.newParity = newParity;
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

    public String getNewGroupName() {
        return newGroupName;
    }

    public void setNewGroupName(String newGroupName) {
        this.newGroupName = newGroupName;
    }

    public String getNewSubjectName() {
        return newSubjectName;
    }

    public void setNewSubjectName(String newSubjectName) {
        this.newSubjectName = newSubjectName;
    }

    public String getNewWeekDay() {
        return newWeekDay;
    }

    public void setNewWeekDay(String newWeekDay) {
        this.newWeekDay = newWeekDay;
    }

    public Boolean getNewParity() {
        return newParity;
    }

    public void setNewParity(Boolean newParity) {
        this.newParity = newParity;
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
