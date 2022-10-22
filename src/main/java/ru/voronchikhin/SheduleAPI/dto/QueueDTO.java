package ru.voronchikhin.SheduleAPI.dto;

import javax.validation.constraints.NotBlank;

public class QueueDTO {

    private String fullName;

    private String switcherFullName;
    private String note;
    private String groupName;
    private String subjectName;

    public QueueDTO() {
    }

    public QueueDTO(String fullName, String note, String groupName, String subjectName) {
        this.fullName = fullName;
        this.note = note;
        this.groupName = groupName;
        this.subjectName = subjectName;
    }

    public QueueDTO(String fullName, String switcherFullName, String note, String groupName, String subjectName) {
        this.fullName = fullName;
        this.switcherFullName = switcherFullName;
        this.note = note;
        this.groupName = groupName;
        this.subjectName = subjectName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getSwitcherFullName() {
        return switcherFullName;
    }

    public void setSwitcherFullName(String switcherFullName) {
        this.switcherFullName = switcherFullName;
    }
}
