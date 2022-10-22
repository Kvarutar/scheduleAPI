package ru.voronchikhin.SheduleAPI.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.voronchikhin.SheduleAPI.dto.CommentDTO;
import ru.voronchikhin.SheduleAPI.services.CommentService;
import ru.voronchikhin.SheduleAPI.util.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentValidator commentValidator;


    public CommentController(CommentService commentService, CommentValidator commentValidator) {
        this.commentService = commentService;
        this.commentValidator = commentValidator;
    }

    @GetMapping()
    public CommentDTO getComment(@RequestParam(value = "group") String groupName,
                      @RequestParam(value = "subject") String subjectName,
                      @RequestParam(value = "date") String commentDate){
        return commentService.findByGroupNameAndSubjectNameAndCommentDate(groupName, subjectName, commentDate);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addComment(@RequestBody @Valid CommentDTO commentDTO,
                                                 BindingResult bindingResult){
        commentValidator.validate(commentDTO, bindingResult);
        if (bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);

            throw new CommentAddingException(errorBuilder.buildError());
        }

        commentService.save(commentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> deleteComment(@RequestBody @Valid CommentDTO commentDTO,
                                                  BindingResult bindingResult){
        commentValidator.validate(commentDTO, bindingResult);
        if (bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);

            throw new CommentDeletingException(errorBuilder.buildError());
        }

        commentService.delete(commentDTO.getGroupName(), commentDTO.getSubjectName(), commentDTO.getCommentDate());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<HttpStatus> updateComment(@RequestBody @Valid CommentDTO commentDTO,
                                                    BindingResult bindingResult){
        commentValidator.validate(commentDTO, bindingResult);
        if (bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);

            throw new CommentUpdateException(errorBuilder.buildError());
        }

        commentService.update(commentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler({CommentDeletingException.class, CommentUpdateException.class, CommentAddingException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException exc){
        ErrorResponse errorResponse = new ErrorResponse(
                exc.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
