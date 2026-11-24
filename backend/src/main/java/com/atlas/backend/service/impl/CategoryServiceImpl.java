package com.atlas.backend.service.impl;
import com.atlas.backend.dto.CategoryRequest;
import com.atlas.backend.dto.CategoryResponse;
import com.atlas.backend.dto.CategoryStatusRequest;
import com.atlas.backend.entity.Category;
import com.atlas.backend.exception.DuplicateResourceException;
import com.atlas.backend.exception.ResourceNotFoundException;
import com.atlas.backend.repository.CategoryRepository;
import com.atlas.backend.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request){
        String normalizedName=request.name().trim();

        if(categoryRepository.existsByNameIgnoreCase(normalizedName)){
           throw new DuplicateResourceException("Category already exists: " + normalizedName);
        }

        Category category=new Category();
        copyRequestToCategory(category,request);
        category.setStatus("ACTIVE");

        Category savedCategory=categoryRepository.save(category);
        return toResponse(savedCategory);


    }


    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long categoryId) {
       Category category= findCategoryOrThrow(categoryId);
       return toResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        Category category= findCategoryOrThrow(categoryId);

        String normalizedName=request.name().trim();
        if(categoryRepository.existsByNameIgnoreCaseAndIdNot(normalizedName,categoryId)){
            throw new DuplicateResourceException("Category already exists:" + normalizedName);

        }
        copyRequestToCategory(category, request);
        Category savedCategory =categoryRepository.saveAndFlush(category);
        return toResponse(savedCategory);

    }



    @Override
    @Transactional
    public CategoryResponse updateCategoryStatus(Long categoryId, CategoryStatusRequest request) {
        Category category= findCategoryOrThrow(categoryId);
        category.setStatus(request.status());
        Category savedCategory = categoryRepository.saveAndFlush(category);
        return toResponse(savedCategory);

    }


    @Override
    @Transactional
    public void deleteCategoryById(Long categoryId){
        Category category= findCategoryOrThrow(categoryId);
        category.setStatus("INACTIVE");

    }


    private void copyRequestToCategory(Category category, CategoryRequest request){
        category.setName(request.name().trim());
        category.setDescription(request.description() == null ? null : request.description().trim());

    }

    private Category findCategoryOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getStatus(),
                category.getCreatedAt(),
                category.getUpdatedAt());
    }




}