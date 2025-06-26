package com.blogapplication.blog_app_apis.services;

import java.util.List;

import com.blogapplication.blog_app_apis.payloads.JwtAuthRequest;
import com.blogapplication.blog_app_apis.payloads.UserDto;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
