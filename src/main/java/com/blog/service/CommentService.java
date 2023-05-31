package com.blog.service;

import com.blog.exception.BlogAPIException;
import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto saveComment(Long postId,CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long postId); // get comment by post id

    // List is not there because we are returning only one comment
    CommentDto getCommentById(long postId , long commentId); // supplying post id & comment id


    CommentDto updateComment(long postId, CommentDto commentDto);

    CommentDto updateComment(long postId, long id, CommentDto commentDto);

    void deleteComment(long postId, long id);
}
