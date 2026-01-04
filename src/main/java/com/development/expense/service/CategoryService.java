package com.development.expense.service;

import com.development.expense.constant.CodeConstant;
import com.development.expense.constant.MessageConstant;
import com.development.expense.dto.ApiResponse;
import com.development.expense.dto.CategoryDto;
import com.development.expense.entity.CategoryEntity;
import com.development.expense.repository.CategoryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public ApiResponse getAllCategories(int page , int size){
        ApiResponse response= new ApiResponse();
        List<CategoryDto> list = new ArrayList<>();
        try {
            Pageable pageable= PageRequest.of(page,size);
            Page<CategoryEntity> data = categoryRepository.findAll(pageable);
            for(CategoryEntity category: data){
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setId(category.getId());
                categoryDto.setName(category.getName());
                list.add(categoryDto);

            }
        }catch (Exception e){
            response.setCode(CodeConstant.INTERNAL_SERVER_ERROR);
            response.setMessage(MessageConstant.INTERNAL_SERVER_ERROR);
            return response;

        }
        response.setCode(CodeConstant.SUCCESS);
        response.setMessage(MessageConstant.SUCCESS);
        response.setData(list);
        return response;

    }
    public ApiResponse getCategoryById(Long id){
        ApiResponse response= new ApiResponse();
        var data = categoryRepository.findById(id).orElse(null);
        if(data == null){
            response.setCode(CodeConstant.NOT_FOUND);
            response.setMessage(MessageConstant.NOT_FOUND);
            return response;
        }
        response.setCode(CodeConstant.SUCCESS);
        response.setMessage(MessageConstant.SUCCESS);
        response.setData(data);
        return response;
    }

    public String add(CategoryEntity category){
       category.setCreated_at(new Timestamp(System.currentTimeMillis()));
        categoryRepository.save(category);
        return "ok";

    }
    public String update(CategoryEntity category){
        if(category.getName()==null || category.getName().isEmpty()){
            return "category name is not null or empty";
        }
        var find = categoryRepository.findById(category.getId());
        if(find.isEmpty()) {
            return "Id not found";
        }
        Timestamp now = new Timestamp((System.currentTimeMillis()));
        category.setUpdated_at(now);
        category.setCreated_at(find.get().getCreated_at());
        categoryRepository.save(category);
        return "updated";

    }
    public String delete(Long id){
        var find = categoryRepository.findById(id);
        if(find.isEmpty()) {
            return "Id not found";
        }
        categoryRepository.delete(find.get());
        return "Deleted";

    }






}


