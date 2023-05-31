package com.blog.controller;

import com.blog.exception.BlogAPIException;
import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;    // Calling service layer & do dependency injection

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/posts/1/comments  // store the comments in the DB
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(

            @PathVariable("postId") long postId,
            @RequestBody CommentDto commentDto

    ) {

        CommentDto dto = commentService.saveComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")    // to get the records
    public List<CommentDto>getCommentsByPostId(@PathVariable("postId") long postId){
       return commentService.getCommentByPostId(postId);

    }

    // Get Comment Bt Id ::

    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById (@PathVariable("postId") long postId,@PathVariable("commentId") long commentId) {

        CommentDto dto = commentService.getCommentById(postId, commentId);// now controller will call service layer
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    //http://localhost:8080/api/posts/{postId/comments/{id}
    @PutMapping("/posts/{postId}/comments/{id}")    //For putting the comment for specific id
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id,
            @RequestBody CommentDto commentDto       // @RequestBody take the content of JSON and put that into comment


            ) {

        CommentDto dto = commentService.updateComment(postId , id ,  commentDto);
          return new ResponseEntity<>(dto , HttpStatus.OK);

    }

    //http://localhost:8080/api/posts/{postId/comments/{id}
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(  // For deleting the comment

        @PathVariable("postId") long postId,   // reading postId
        @PathVariable("id") long id           // reading commentId

                                                  ){

        commentService.deleteComment(postId,id);
        return new ResponseEntity<>("Comment is Deleted" ,HttpStatus.OK);

}
}