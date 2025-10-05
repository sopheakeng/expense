package com.development.expense.service;

import com.development.expense.entity.CategoryEntity;
import com.development.expense.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public List<CategoryEntity> getAllCategories(){
        return categoryRepository.findAll();
    }
}
