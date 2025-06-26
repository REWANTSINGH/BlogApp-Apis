package com.blogapplication.blog_app_apis.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blogapplication.blog_app_apis.entities.Comment;

// import com.blogapplication.blog_app_apis.entities.Category;//important to notice used CategoryDto
// import com.blogapplication.blog_app_apis.entities.User;// Aviod infinite recursion

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    
    private Integer postId; 
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user; 

    private Set<CommentDto> comments=new HashSet<>();

}
