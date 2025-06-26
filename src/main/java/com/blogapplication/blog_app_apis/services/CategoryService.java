package com.blogapplication.blog_app_apis.services;

import java.util.List;

import com.blogapplication.blog_app_apis.payloads.CategoryDto;


public interface CategoryService {//this interface concept is used for loose coupling
    
    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    //delete
    void deleteCategory(Integer categoryId);

    //get
    CategoryDto getCategory(Integer categoryId);

    //get All
    List<CategoryDto> getCategories();
    


}
