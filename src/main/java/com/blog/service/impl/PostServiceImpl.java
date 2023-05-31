package com.blog.service.impl;

import com.blog.entities.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repositories.PostRepository;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) { // Constructor based injections
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

     Post post = mapToEntity((postDto));

        Post savedPost  = postRepository.save(post);//saving the record

        PostDto dto = mapToDto(savedPost);

        return  dto;
    }


    @Override
    public PostResponse getAllPosts(int pageNo , int pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?     //Ternary Operator
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize,sort);
       Page<Post>content= postRepository.findAll(pageable);
        List<Post> posts = content.getContent();

   List<PostDto> dtos= posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());

   PostResponse postResponce= new PostResponse();
   postResponce.setContent(dtos);
   postResponce.setPageNo(content.getNumber()); // getNumber built in method present inside the page obj.gives current page no.
   postResponce.setPageSize(content.getSize()); //getSize gives size of the page automatically
   postResponce.setTotalPages(content.getTotalPages());
   postResponce.setTotalElements(content.getTotalElements());
   postResponce.setLast(content.isLast());

        return postResponce;

    }

    @Override
    public PostDto getPostById(long id) {                       // 02
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found with id: " + id)
        );

        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
       Post post= postRepository.findById(id).orElseThrow(
               ()->new ResourceNotFoundException("Post Not Found with id:" + id)
        );

       post.setTitle(postDto.getTitle());
       post.setContent(postDto.getContent());
       post.setDescription(postDto.getDescription());

       Post updatedPost = postRepository.save(post);

      return  mapToDto(updatedPost);

    }

    @Override
    public void deletePost(long id) {

        Post post= postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post Not Found with id:" + id)
        );
        postRepository.deleteById(id);

    }

    Post mapToEntity(PostDto postDto) {

        Post post = mapper.map(postDto, Post.class);// mapper.map() method automatically copy the data from postDto to Post.class
//        Post post = new Post(); // Entity obj
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());           // all these lines of code got skipped by writting mapper code
        return post;

    }
    PostDto mapToDto(Post post){

        PostDto dto = mapper.map(post, PostDto.class);//  what we want to map is 'post' to what we need to map = PostDto // so to what we need to map give the class name to that

//        PostDto dto = new PostDto() ;
//        dto.setId(post.getId());
//           dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return  dto;
    }
}
