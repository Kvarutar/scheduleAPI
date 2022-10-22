package ru.voronchikhin.SheduleAPI.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "queue")
public class Queue {

    @EmbeddedId
    private QueueKey id;

    @Column(name = "entered_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredAt;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Queue() {
    }

    public Queue(QueueKey id) {
        this.id = id;
    }

    public Queue(QueueKey id, Date enteredAt, String note, Group group, Subject subject) {
        this.id = id;
        this.enteredAt = enteredAt;
        this.note = note;
        this.group = group;
        this.subject = subject;
    }

    public QueueKey getId() {
        return id;
    }

    public void setId(QueueKey id) {
        this.id = id;
    }

    public Date getEnteredAt() {
        return enteredAt;
    }

    public void setEnteredAt(Date enteredAt) {
        this.enteredAt = enteredAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
}
