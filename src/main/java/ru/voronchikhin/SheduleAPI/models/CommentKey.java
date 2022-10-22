package ru.voronchikhin.SheduleAPI.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CommentKey implements Serializable {
    @Column(name = "group_id")
    private int groupId;

    @Column(name = "subject_id")
    private int subjectId;

    @Column(name = "comment_date")
    private String commentDate;

    public CommentKey() {
    }

    public CommentKey(int groupId, int subjectId, String commentDate) {
        this.groupId = groupId;
        this.subjectId = subjectId;
        this.commentDate = commentDate;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentKey that = (CommentKey) o;
        return groupId == that.groupId && subjectId == that.subjectId && Objects.equals(commentDate, that.commentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, subjectId, commentDate);
    }
}
