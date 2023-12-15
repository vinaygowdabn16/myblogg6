package com.blopapii.service;

import com.blopapii.payload.PostDto;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

   public PostDto getPostById(long id);

   public  List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

   public void deletePost(long id);

    public PostDto updatePost(long id, PostDto postDto);
}
