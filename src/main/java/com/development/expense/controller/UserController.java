package com.development.expense.controller;

import com.development.expense.dto.ApiResponse;
import com.development.expense.dto.ForgotPasswordDto;
import com.development.expense.dto.UserDto;
import com.development.expense.entity.CategoryEntity;
import com.development.expense.entity.UserEntity;
import com.development.expense.rest.dto.ForgotPasswordVerifyDto;
import com.development.expense.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserEntity>> getAlluser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<String> add(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(userService.add(userEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateuser(id,userDto);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto){
        return new ResponseEntity<>(userService.forgotPassword(forgotPasswordDto), HttpStatus.OK);
    }
    @PostMapping("/forgot-password-verify")
    public ResponseEntity<ApiResponse> forgotPasswordVerify(@RequestBody ForgotPasswordVerifyDto request ){
        return new ResponseEntity<>(userService.forgotPasswordVerify(request), HttpStatus.OK);
    }

}
