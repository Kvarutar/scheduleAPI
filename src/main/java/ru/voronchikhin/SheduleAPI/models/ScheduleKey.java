package ru.voronchikhin.SheduleAPI.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ScheduleKey implements Serializable {

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "week_day")
    private String weekDay;

    @Column(name = "parity")
    private Boolean parity;

    public ScheduleKey() {
    }

    public ScheduleKey(Integer groupId, Integer subjectId, String weekDay, Boolean parity) {
        this.groupId = groupId;
        this.subjectId = subjectId;
        this.weekDay = weekDay;
        this.parity = parity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleKey that = (ScheduleKey) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(subjectId, that.subjectId) && Objects.equals(weekDay, that.weekDay) && Objects.equals(parity, that.parity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, subjectId, weekDay, parity);
    }
}
