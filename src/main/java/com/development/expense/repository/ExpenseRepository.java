package com.development.expense.repository;

import com.development.expense.entity.CategoryEntity;
import com.development.expense.entity.ExpenseEntity;
import com.development.expense.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ExpenseRepository extends JpaRepository <ExpenseEntity,Long>{
}


