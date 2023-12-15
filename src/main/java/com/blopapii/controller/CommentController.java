package com.blopapii.controller;

import com.blopapii.payload.CommentDto;
import com.blopapii.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value="id") long postId,
                                                    @RequestBody CommentDto commentDto){

        CommentDto savedDto = commentService.createComment(postId,commentDto);

        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }


    @GetMapping("/posts/{id}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable(value="id") long postId){

       List<CommentDto> dtos = commentService.getCommentsByPostId(postId);

       return dtos;
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")

    public ResponseEntity<String> deleteCommentById(@PathVariable("postId")long postId,
                                                    @PathVariable("id")long id){

        commentService.deleteCommentById(postId, id);

        return new ResponseEntity<>("comment is deleted",HttpStatus.OK);
    }


    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId")long postId,
                                                    @PathVariable("id")long id){

       CommentDto dto =  commentService.getCommentById(postId, id);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("postId")long postId,
                                                        @PathVariable("id")long id,@RequestBody CommentDto commentDto){
    CommentDto dto =   commentService.updateCommentById(postId,id,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
