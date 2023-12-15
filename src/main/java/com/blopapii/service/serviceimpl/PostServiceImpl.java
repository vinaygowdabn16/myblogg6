package com.blopapii.service.serviceimpl;

import com.blopapii.entity.Post;
import com.blopapii.exceptions.ResourceNotFoundException;
import com.blopapii.payload.PostDto;
import com.blopapii.repository.PostRepository;
import com.blopapii.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepo;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);


        Post savedPost = postRepo.save(post);

        PostDto dto = mapToDto(savedPost);

        return dto;
    }

    @Override
    public PostDto getPostById(long id) {

        Post post = postRepo.findById(id).orElseThrow(

                ()-> new ResourceNotFoundException(id)
        );

        PostDto dto = mapToDto(post);

        return dto;
    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> posts = postRepo.findAll(pageable);
        List<Post> content = posts.getContent();

        List<PostDto> postDtos =content.stream().map(post->mapToDto(post)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public void deletePost(long id) {
       Post post = postRepo.findById(id).orElseThrow(

                ()-> new ResourceNotFoundException(id)
        );

       postRepo.deleteById(id);
       return;

    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(id)
        );

        Post updatablePost = mapToEntity(postDto);
        updatablePost.setId(post.getId());

        Post updatedPostInfo = postRepo.save(updatablePost);
       return mapToDto(updatedPostInfo);

    }

    public PostDto mapToDto(Post post){

        PostDto dto = modelMapper.map(post,PostDto.class);

//        PostDto dto = new PostDto();
//         dto.setId(post.getId());
//         dto.setTitle(post.getTitle());
//         dto.setDescription(post.getDescription());
//         dto.setContent(post.getContent());

          return dto;
      }

     public Post mapToEntity(PostDto postDto){

        Post post = modelMapper.map(postDto,Post.class);


//         Post post =new Post();
//         post.setId(postDto.getId());
//         post.setTitle(postDto.getTitle());
//         post.setDescription(postDto.getDescription());
//         post.setContent(postDto.getContent());

         return post;
      }


}
