package com.blogapplication.blog_app_apis.payloads;


import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    // private int id;

    // @NotNull
    // @NotBlank
    // private String name;

    // @Email
    // private String email;

    // @NotNull
    // private String password;

    // @NotNull
    // private String about;
    private int id;

    @NotBlank(message = "Name is required")
    @Size(min=4, message= "Name must be at least 4 characters long")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min=8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "About is required")
    private String about;

    private Set<RoleDto> roles= new HashSet<>();

}
