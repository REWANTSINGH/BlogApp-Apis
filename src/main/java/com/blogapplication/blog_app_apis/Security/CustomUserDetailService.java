package com.blogapplication.blog_app_apis.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogapplication.blog_app_apis.entities.User;
import com.blogapplication.blog_app_apis.exceptions.ResourceNotFoundException;
import com.blogapplication.blog_app_apis.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("[DEBUG] Attempting to load user by username: " + username);
        User user = this.userRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email : " + username, 0));
        System.out.println("[DEBUG] User found: " + user.getEmail());
        // 🔄 Wrap User inside CustomUserDetails
        return new CustomUserDetails(user);
    }
}
