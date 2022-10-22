package ru.voronchikhin.SheduleAPI.models;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule {

    @EmbeddedId
    private ScheduleKey scheduleKey;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "building")
    private String building;

    @Column(name = "room")
    private String room;

    @Column(name = "time")
    private String time;

    public Schedule() {
    }

    public Schedule(ScheduleKey scheduleKey) {
        this.scheduleKey = scheduleKey;
    }

    public Schedule(ScheduleKey scheduleKey, Group group, Subject subject, String teacher, String building, String room, String time) {
        this.scheduleKey = scheduleKey;
        this.group = group;
        this.subject = subject;
        this.teacher = teacher;
        this.building = building;
        this.room = room;
        this.time = time;
    }

    public ScheduleKey getScheduleKey() {
        return scheduleKey;
    }

    public void setScheduleKey(ScheduleKey scheduleKey) {
        this.scheduleKey = scheduleKey;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
