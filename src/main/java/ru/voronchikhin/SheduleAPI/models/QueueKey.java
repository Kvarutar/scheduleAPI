package ru.voronchikhin.SheduleAPI.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class QueueKey implements Serializable {

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "full_name")
    private String fullName;

    public QueueKey() {
    }

    public QueueKey(Integer groupId, Integer subjectId, String fullName) {
        this.groupId = groupId;
        this.subjectId = subjectId;
        this.fullName = fullName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
