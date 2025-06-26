package com.blogapplication.blog_app_apis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapplication.blog_app_apis.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    
    // Method to find a role by its name
    Role findByName(String name);
    
    // Optional: You can add more methods if needed, such as finding roles by other attributes
    // List<Role> findBySomeOtherAttribute(String attribute);
    
    // Note: Ensure that the Role entity is defined in your project with appropriate fields and annotations

}
    

