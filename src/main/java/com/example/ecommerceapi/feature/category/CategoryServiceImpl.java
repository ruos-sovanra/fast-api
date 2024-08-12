package com.example.ecommerceapi.feature.category;

import com.example.ecommerceapi.domain.Category;
import com.example.ecommerceapi.feature.category.dto.CategoryRequest;
import com.example.ecommerceapi.feature.category.dto.CategoryResponse;
import com.example.ecommerceapi.feature.category.dto.CategoryUpdateRequest;
import com.example.ecommerceapi.mapper.CategoryMapper;
import com.example.ecommerceapi.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CustomPageUtils<CategoryResponse> getAllCategories(int page, int size) {

        Page<Category> categories = categoryRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));

        return CustomPagination(categories.map(categoryMapper::toCategoryResponse));

    }

    public CustomPageUtils<CategoryResponse> CustomPagination(Page<CategoryResponse> page){

        CustomPageUtils<CategoryResponse> customPage = new CustomPageUtils<>();

        //check if page has next
        if(page != null && page.hasPrevious()){
            customPage.setPrevious(true); // Set to true if there is a previous page
        } else {
            customPage.setPrevious(false); // Set to false if there is no previous page
        }

        if(page != null && page.hasNext()){
            customPage.setNext(true); // Set to true if there is a next page
        } else {
            customPage.setNext(false); // Set to false if there is no next page
        }

        //set total
        customPage.setTotal((int) page.getTotalElements());
        customPage.setTotalElements(page.getTotalElements());

        //set total pages
        customPage.setResults(page.getContent());

        return customPage;
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Category not found"));
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.requestToCategory(categoryRequest);
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Category not found"));
        category.setName(categoryRequest.name());
        category.setImage(categoryRequest.image());
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategoryImage(Long id,CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Category not found"));
        category.setImage(categoryUpdateRequest.image());
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Category not found"));
        categoryRepository.delete(category);
    }
}
