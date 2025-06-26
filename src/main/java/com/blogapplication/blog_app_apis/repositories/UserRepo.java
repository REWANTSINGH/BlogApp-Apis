package com.blogapplication.blog_app_apis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapplication.blog_app_apis.entities.User;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Integer>{  //<a,b> a=jis entity ke kaam krna chahte ho, b=usme id kis type ki hai
    
    Optional<User> findByEmail(String email);


}
