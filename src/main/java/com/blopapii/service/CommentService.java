package com.blopapii.service;


import com.blopapii.payload.CommentDto;

import java.util.List;

public interface CommentService {

    public CommentDto createComment(long postId,CommentDto commentDto);

    public List<CommentDto> getCommentsByPostId(long postId);

    public void deleteCommentById(long postId,long id);

    public CommentDto getCommentById(long postId, long id);

    public CommentDto updateCommentById(long postId, long id, CommentDto commentDto);
}
