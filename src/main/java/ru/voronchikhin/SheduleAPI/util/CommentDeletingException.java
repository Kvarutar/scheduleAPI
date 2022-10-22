package ru.voronchikhin.SheduleAPI.util;

import ru.voronchikhin.SheduleAPI.models.Comment;

public class CommentDeletingException extends RuntimeException{
    public CommentDeletingException(String msg){
        super(msg);
    }
}
