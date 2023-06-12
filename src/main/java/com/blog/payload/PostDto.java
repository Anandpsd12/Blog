package com.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;

    @NotEmpty                                                           // spring validation
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty                                                           // spring validation
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;


    @NotEmpty(message = "Should not be Empty")                          // spring validation
    private String content;

}
