package com.development.expense.controller;

import com.development.expense.dto.ApiResponse;
import com.development.expense.dto.CategoryDto;
import com.development.expense.entity.CategoryEntity;
import com.development.expense.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> categories(@RequestParam(defaultValue = "0") int page ,@RequestParam(defaultValue = "10") int size){
        return  ResponseEntity.ok(categoryService.getAllCategories(page , size));
    }
    @PostMapping("")
    public String add(@RequestBody CategoryEntity categoryEntity){
        System.out.println("categoryEntity = " + categoryEntity);
        return categoryService.add(categoryEntity);
    }
    @PutMapping("")
    public String update(@RequestBody CategoryEntity categoryEntity){
        System.out.println("categoryEntity = " + categoryEntity);
        return categoryService.update(categoryEntity);
    }
    @DeleteMapping("/{id}")
    public String delete (@PathVariable Long id){
        return categoryService.delete(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findByID (@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }


}