package com.atlas.backend.service;

import com.atlas.backend.dto.CategoryRequest;
import com.atlas.backend.dto.CategoryResponse;
import com.atlas.backend.dto.CategoryStatusRequest;
import com.atlas.backend.entity.Category;

import java.util.List;

public interface CategoryService{

     CategoryResponse createCategory(CategoryRequest request);

     List<CategoryResponse> getAllCategories();

     CategoryResponse getCategoryById(Long categoryId);

     CategoryResponse updateCategory(Long categoryId, CategoryRequest request);

     CategoryResponse updateCategoryStatus(Long categoryId, CategoryStatusRequest request);

     void deleteCategoryById(Long categoryId);
}