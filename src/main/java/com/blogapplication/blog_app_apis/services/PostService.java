package com.blogapplication.blog_app_apis.services;

import java.util.List;

import com.blogapplication.blog_app_apis.entities.Post;
import com.blogapplication.blog_app_apis.payloads.PostDto;
import com.blogapplication.blog_app_apis.payloads.PostResponse;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get single post
    PostDto getPostById(Integer postId);

    //get all post
    PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

    //get all post by category
    List<PostDto> getPostsByCategory(Integer categoryId);

    //get post by user
    List<PostDto> getPostsByUser(Integer userId);

    //search
    List<PostDto> searchPosts(String keyword);
}
