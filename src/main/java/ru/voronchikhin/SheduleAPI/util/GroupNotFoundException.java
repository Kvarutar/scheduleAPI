package ru.voronchikhin.SheduleAPI.util;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException(String msg){
        super(msg);
    }
}
