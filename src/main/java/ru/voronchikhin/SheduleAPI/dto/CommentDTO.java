package ru.voronchikhin.SheduleAPI.dto;

import ru.voronchikhin.SheduleAPI.models.CommentKey;
import ru.voronchikhin.SheduleAPI.models.Group;
import ru.voronchikhin.SheduleAPI.models.Subject;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

public class CommentDTO {
    @NotBlank
    private String groupName;

    @NotBlank
    private String subjectName;

    @NotBlank
    private String commentDate;

    private String note;

    public CommentDTO() {
    }

    public CommentDTO(String groupName, String subjectName, String commentDate, String note) {
        this.groupName = groupName;
        this.subjectName = subjectName;
        this.commentDate = commentDate;
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

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
