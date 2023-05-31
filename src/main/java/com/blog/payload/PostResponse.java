package com.blog.payload;

import lombok.Data;

import java.util.List;
@Data            // @Data gives getters & Setters
public class PostResponse {

   private List<PostDto>content;   // applying encapsulation bt making variables private
   private int pageNo;
   private int pageSize;
   private long totalElements;
   private int totalPages;
   private   boolean isLast;
}
