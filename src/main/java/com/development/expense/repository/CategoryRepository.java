package com.development.expense.repository;

import com.development.expense.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <CategoryEntity,Long>{
}
