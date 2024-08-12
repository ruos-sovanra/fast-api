package com.example.ecommerceapi.feature.category;

import com.example.ecommerceapi.feature.category.dto.CategoryRequest;
import com.example.ecommerceapi.feature.category.dto.CategoryResponse;
import com.example.ecommerceapi.feature.category.dto.CategoryUpdateRequest;
import com.example.ecommerceapi.feature.product.dto.ProductResponse;
import com.example.ecommerceapi.utils.BaseResponse;
import com.example.ecommerceapi.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<CustomPageUtils<CategoryResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size){
        CustomPageUtils<CategoryResponse> postResponseCustomPage = categoryService.getAllCategories(page, size);
        return ResponseEntity.ok(postResponseCustomPage);
    }

    @PostMapping
    @Operation(summary = "Create a new category")
    public BaseResponse<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return BaseResponse.<CategoryResponse>createSuccess()
                .setPayload(categoryService.createCategory(categoryRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category")
    public BaseResponse<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){
        return BaseResponse.<CategoryResponse>ok()
                .setPayload(categoryService.updateCategory(id, categoryRequest));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a category image")
    public BaseResponse<CategoryResponse> updateCategoryImage(@PathVariable Long id, @RequestBody CategoryUpdateRequest categoryRequest){
        return BaseResponse.<CategoryResponse>ok()
                .setPayload(categoryService.updateCategoryImage(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category")
    public BaseResponse<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return BaseResponse.<String>ok()
                .setPayload("Category deleted successfully");
    }

}
