package com.example.ecommerceapi.mapper;

import com.example.ecommerceapi.domain.Category;
import com.example.ecommerceapi.feature.category.dto.CategoryRequest;
import com.example.ecommerceapi.feature.category.dto.CategoryResponse;
import com.example.ecommerceapi.feature.category.dto.CategoryUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // CategoryResponse toCategoryResponse(Category category);

    CategoryResponse toCategoryResponse(Category category);
    // Category toCategory(CategoryRequest categoryRequest);
    Category requestToCategory(CategoryRequest categoryRequest);
    //Category toCategory(CategoryUpdateRequest categoryRequest);
//    Category updateRequestToCategory(CategoryUpdateRequest categoryRequest);
}
