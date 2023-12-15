package com.blopapii.service.serviceimpl;

import com.blopapii.entity.Comment;
import com.blopapii.entity.Post;
import com.blopapii.exceptions.ResourceNotFoundException;
import com.blopapii.payload.CommentDto;
import com.blopapii.repository.CommentRepository;
import com.blopapii.repository.PostRepository;
import com.blopapii.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private PostRepository postRepo;

    private CommentRepository commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepo,CommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }



    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setPost(post);

        Comment savedComment = commentRepo.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setBody(savedComment.getBody());
        dto.setEmail(savedComment.getEmail());


        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        Post post = postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(postId)
        );


        List<Comment> comments = commentRepo.findByPostId(postId);

        List<CommentDto> dtos = comments.stream().map(comment -> mapToCommentDto(comment)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public void deleteCommentById(long postId, long id) {

        Post post = postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(postId)
        );

       Comment comment = commentRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(id)
        );

       commentRepo.deleteById(id);
           return;

    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(postId)
        );

        Comment comment = commentRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(id)
        );

        CommentDto dto = this.mapToCommentDto(comment);

        return dto;
    }

    @Override
    public CommentDto updateCommentById(long postId, long id, CommentDto commentDto) {
        postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(postId)
        );

        Comment comment = commentRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(id)
        );

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(comment.getBody());

        Comment updatedComment = commentRepo.save(comment);

        CommentDto dto = this.mapToCommentDto(updatedComment);

        return dto;
    }



    public CommentDto mapToCommentDto(Comment comment){

        CommentDto dto = modelMapper.map(comment, CommentDto.class);

        return dto;
    }



}
