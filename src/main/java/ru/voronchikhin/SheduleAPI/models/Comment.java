package ru.voronchikhin.SheduleAPI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment {

    @EmbeddedId
    private CommentKey id;

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

    public Comment() {
    }

    public Comment(CommentKey id) {
        this.id = id;
    }

    public Comment(CommentKey id, String note, Group group, Subject subject) {
        this.id = id;
        this.note = note;
        this.group = group;
        this.subject = subject;
    }

    public CommentKey getId() {
        return id;
    }

    public void setId(CommentKey id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(note, comment.note) && Objects.equals(group, comment.group) && Objects.equals(subject, comment.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, note, group, subject);
    }
}
