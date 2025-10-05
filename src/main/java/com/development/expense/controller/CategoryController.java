package com.development.expense.controller;

import com.development.expense.entity.CategoryEntity;
import com.development.expense.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("")
    public List<CategoryEntity> catgeries(){
        return categoryService.getAllCategories();
    }
}