package com.development.expense.repository;

import com.development.expense.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserEntity,Long> {
    public  UserEntity findUserEntityByUsername(String username);

}
