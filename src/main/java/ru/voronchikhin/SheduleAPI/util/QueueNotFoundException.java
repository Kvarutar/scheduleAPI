package ru.voronchikhin.SheduleAPI.util;

public class QueueNotFoundException extends RuntimeException{
    public QueueNotFoundException(String msg){
        super(msg);
    }
}
