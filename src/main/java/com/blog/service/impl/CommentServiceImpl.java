package com.blog.service.impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;


    private ModelMapper mapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository , ModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper=mapper;
    }

    @Override
    public CommentDto saveComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found with id: " + postId)

        );


        Comment comment = mapToEntity(commentDto);

        comment.setPost(post);  // we want comment set for this post
        Comment newComment = commentRepository.save(comment);  // saving comments

        return mapToDto(newComment);


    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList()); //converting all the streams into DTOs

    }

    @Override
    public CommentDto getCommentById(long postId, long commentId)  {

        // Checking post exists with id or not

        Post post = postRepository.findById(postId).orElseThrow(   // checking does the post exist with id
                () -> new ResourceNotFoundException("Post Not Found with id: " + postId)

        );

        // checking Comments exists with id or not

        Comment comment= commentRepository.findById(commentId).orElseThrow(

                () ->new  ResourceNotFoundException("comment not Found with id : " + commentId )

        );

        // Comparing if post exist with same id number or not

        if(!comment.getPost().getId().equals(post.getId())) { // post.getId():: get the post id from comment table

            throw new BlogAPIException ("Comment does not belong to post");
        }


        return mapToDto(comment); // comment obj converts back to Dto
    }

    @Override
    public CommentDto updateComment(long postId, CommentDto commentDto) {
        return null;
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {

      //postId=2
       Post post = postRepository.findById(postId).orElseThrow(

                ()-> new ResourceNotFoundException("Post not found with id :" + postId)

        );

       //comment.getPost().getPostId()  postId= 2
      Comment comment =  commentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Comment not found with id :" + id)

        );

      if(!comment.getPost().getId().equals(post.getId())){

          throw new BlogAPIException("Comment does not belong to post");    // Instead of using Lambdas expression . here we use throw
      }

      comment.setName(commentDto.getName());
      comment.setEmail(comment.getEmail());
      comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);



    }

    @Override
    public void deleteComment(long postId, long id) {


        //postId=2
        Post post = postRepository.findById(postId).orElseThrow(

                ()-> new ResourceNotFoundException("Post not found with id :" + postId)

        );

        //comment.getPost().getPostId()  postId= 2
        Comment comment =  commentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Comment not found with id :" + id)

        );

        if(!comment.getPost().getId().equals(post.getId())){

            throw new BlogAPIException("Comment does not belong to post");
        }


        commentRepository.deleteById(id);    // deleting the comment

    }


    Comment mapToEntity(CommentDto commentDto){


        Comment comment = mapper.map(commentDto, Comment.class);

         //        Comment comment = new Comment();
        //        comment.setBody(commentDto.getBody());
       //        comment.setEmail(commentDto.getEmail());
      //        comment.setName(commentDto.getName());
        return  comment;
    }
    CommentDto mapToDto(Comment comment){

        CommentDto dto = mapper.map(comment, CommentDto.class);


//        CommentDto dto = new CommentDto();
//        dto.setId(comment.getId());
//        dto.setBody(comment.getBody());
//        dto.setEmail(comment.getEmail());
//        dto.setName(comment.getName());
        return  dto;
    }

}






