package com.blogapplication.blog_app_apis.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    
    private Integer categoryId;
    @NotBlank
    @Size(min=4, message="min size of category desc is 10")
    private String categoryTitle;
    @NotBlank
    @Size(min=10, message="min size of category desc is 10")
    private String categoryDescription;
}
