package com.example.ecommerceapi.feature.category;


import com.example.ecommerceapi.feature.category.dto.CategoryRequest;
import com.example.ecommerceapi.feature.category.dto.CategoryResponse;
import com.example.ecommerceapi.feature.category.dto.CategoryUpdateRequest;
import com.example.ecommerceapi.utils.CustomPageUtils;

import java.util.List;

public interface CategoryService {
    CustomPageUtils<CategoryResponse> getAllCategories(int page, int size);
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);
    void deleteCategory(Long id);
    CategoryResponse updateCategoryImage(Long id, CategoryUpdateRequest categoryUpdateRequest);
}
