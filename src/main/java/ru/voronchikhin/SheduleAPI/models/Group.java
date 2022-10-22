package ru.voronchikhin.SheduleAPI.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "schedule_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "institute_name")
    private String instituteName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<Schedule> scheduleList;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<Queue> queueList;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    public Group(){

    }

    public Group(String name, String instituteName) {
        this.name = name;
        this.instituteName = instituteName;
    }

    public Group(int id, String name, String instituteName, List<Schedule> scheduleList, List<Queue> queueList,
                 List<Comment> commentList) {
        this.id = id;
        this.name = name;
        this.instituteName = instituteName;
        this.scheduleList = scheduleList;
        this.queueList = queueList;
        this.commentList = commentList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<Queue> getQueueList() {
        return queueList;
    }

    public void setQueueList(List<Queue> queueList) {
        this.queueList = queueList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
