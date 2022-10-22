package ru.voronchikhin.SheduleAPI.util;

public class SubjectNotFoundException extends RuntimeException{
    public SubjectNotFoundException(String msg){
        super(msg);
    }
}
