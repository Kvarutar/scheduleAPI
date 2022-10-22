package ru.voronchikhin.SheduleAPI.util;

public class SubjectAddingError extends RuntimeException{
    public SubjectAddingError(String msg){
        super(msg);
    }
}
