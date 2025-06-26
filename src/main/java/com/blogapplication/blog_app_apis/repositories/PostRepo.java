package com.blogapplication.blog_app_apis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogapplication.blog_app_apis.entities.Category;
import com.blogapplication.blog_app_apis.entities.Post;
import com.blogapplication.blog_app_apis.entities.User;


public interface PostRepo extends JpaRepository<Post, Integer>{
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("select p from Post p where p.postTitle like :key")//optional for old versions of hibernate when numbers cannot be searched
    List<Post> searchByPostTitle(@Param("key") String postTitle);

}
