package com.blogapplication.blog_app_apis.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.blog_app_apis.payloads.ApiResponse;
import com.blogapplication.blog_app_apis.payloads.CategoryDto;
import com.blogapplication.blog_app_apis.services.CategoryService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired 
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catDto) {
        
        CategoryDto createCategory = this.categoryService.createCategory(catDto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

    }

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto,@PathVariable Integer catId) {
        
        CategoryDto updatedCategory = this.categoryService.updateCategory(catDto,catId);
        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.CREATED);

    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
        
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully",true),HttpStatus.OK);

    }
    
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
        
        CategoryDto catDto=this.categoryService.getCategory(catId);
        return new ResponseEntity<CategoryDto>(catDto, HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategory() {
        
        List<CategoryDto> catDto=this.categoryService.getCategories();
        return ResponseEntity.ok(catDto);

    }
    
}
