package ru.voronchikhin.SheduleAPI.util;

public class ScheduleNotFoundException extends RuntimeException{
    public ScheduleNotFoundException(String msg){
        super(msg);
    }
}
