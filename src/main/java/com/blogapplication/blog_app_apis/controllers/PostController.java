package com.blogapplication.blog_app_apis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapplication.blog_app_apis.config.AppConstants;
import com.blogapplication.blog_app_apis.entities.Post;
import com.blogapplication.blog_app_apis.payloads.ApiResponse;
import com.blogapplication.blog_app_apis.payloads.PostDto;
import com.blogapplication.blog_app_apis.payloads.PostResponse;
import com.blogapplication.blog_app_apis.services.FileService;
import com.blogapplication.blog_app_apis.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")
public class PostController {
    
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;
    //create



    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
        @RequestBody PostDto postDto,
        @PathVariable Integer userId, 
        @PathVariable Integer categoryId
        ) 
    {
        
        PostDto createPost=this.postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<PostDto>(HttpStatus.CREATED);
    }


    //get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {

        List<PostDto> posts=this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

    }

    //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {

        List<PostDto> posts=this.postService.getPostsByUser(categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
        
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
        @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
        @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
        @RequestParam(value="sortDir", defaultValue=AppConstants.SORT_DIR, required = false) String sortDir
        //good practice to use AppConstants--values ko hardcore kar diya
    ) {
        PostResponse allPost=(PostResponse) this.postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(allPost, HttpStatus.OK);
    }

    //get post details by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postDto=this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Post Successsfullly deleted", true);
    }

    //update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable Integer postId){
        PostDto updatedPost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);

    }

    //search
    @GetMapping("/posts/search/{keywords}")
    public  ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {//explicit way of passing pathvariable parameter
        List<PostDto> result=this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
    }
    
    
    //post upload image
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage (
        @RequestParam("image") MultipartFile image,
        @PathVariable Integer postId
    )throws IOException
    {

       //can do try catch also instead of IOException

       String fileName= this.fileService.uploadImage(path, image);
       PostDto postDto=this.postService.getPostById(postId);
       postDto.setImageName(fileName);
       PostDto updatePost= this.postService.updatePost(postDto, postId);
       return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

    }

    //method to serve files
    @GetMapping(value="/post/image/{imageName}", produces=MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
    ) throws IOException
    {
        InputStream resource=this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
