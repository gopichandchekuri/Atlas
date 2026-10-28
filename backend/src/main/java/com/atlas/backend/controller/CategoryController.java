package com.atlas.backend.controller;

import com.atlas.backend.entity.Category;
import com.atlas.backend.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController{
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping
    public void deleteAllCategories(){
        categoryService.deleteAllCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
    }




}