package com.blopapii.controller;


import com.blopapii.payload.PostDto;
import com.blopapii.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;



    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){

        if(result.hasErrors()){

            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
        PostDto savedDto = postService.createPost(postDto);


        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id ){

        PostDto dto = postService.getPostById(id);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @GetMapping
    public List<PostDto> getAllPost(@RequestParam(value="pageNo",defaultValue = "0",required = false) int pageNo,
                                    @RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize,
                                    @RequestParam(value ="sortBy",defaultValue="id",required = false) String sortBy,
                                    @RequestParam(value="sortDir",defaultValue="asc",required = false) String sortDir){
        List<PostDto> postDtos = postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
        return postDtos;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);

        return new ResponseEntity<>("post is deleted ",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id")long id,
                                              @RequestBody PostDto postDto){
      PostDto savedPostDto = postService.updatePost(id,postDto);

        return new ResponseEntity<>(savedPostDto,HttpStatus.OK);
    }


}
