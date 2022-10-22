package ru.voronchikhin.SheduleAPI.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorBuilder {
    private BindingResult bindingResult;

    public ErrorBuilder(BindingResult bindingResult){
        this.bindingResult = bindingResult;
    }

    public String buildError(){
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error: errors){
            errorMessage.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }

        return errorMessage.toString();
    }
}
