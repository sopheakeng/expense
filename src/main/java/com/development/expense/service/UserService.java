package com.development.expense.service;

import com.development.expense.constant.CodeConstant;
import com.development.expense.constant.MessageConstant;
import com.development.expense.dto.ApiResponse;
import com.development.expense.dto.ForgotPasswordDto;
import com.development.expense.dto.UserDto;
import com.development.expense.entity.UserEntity;
import com.development.expense.repository.UserRepository;
import com.development.expense.rest.dto.ForgotPasswordVerifyDto;
import com.development.expense.rest.dto.SentOTPRequest;
import com.development.expense.util.GlobalUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final VerificationService verificationService;
    public UserService(UserRepository userRepository, VerificationService verificationService) {

        this.userRepository = userRepository;
        this.verificationService = verificationService;
    }

    public List<UserEntity>findAll(){

        return  userRepository.findAll();
    }

    public String add(UserEntity userEntity) {
        if (userEntity.getUsername().isBlank()) {
            return "You need input  username ";
        }
        if (userEntity.getUsername().contains(" ") || userEntity.getPassword().contains((" "))) {
            return "username or password contains whitespace";
        }
        if(userEntity.getPassword().isBlank()){
            return " YOU need input password";
        }
        if(userEntity.getPassword().length()<6){
            return "YOU Need input Password bigger 6";
        }
        UserEntity findUser = userRepository.findUserEntityByUsername(userEntity.getUsername());
        if(findUser !=null){
            if(findUser.getUsername().equals(userEntity.getUsername())){
                return "username is already taken";
            }
        }
        if(userEntity.getRole()==null){
            return "ROLE is required";
        }
        if(userEntity.getStatus()==null){
            return "STATUS IS REQUIRED";
        }

        userEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(userEntity);
        return "YOU ARE SUCCESS AND CAN ADD ";
    }
    public String deleteById(Long id){
        userRepository.deleteById(id);
        return"YOu are Delete success";
    }
    public String  updateuser(Long id, UserDto userDto) {
        UserEntity find = userRepository.findById(id).orElse(null);
        if(find==null){
            return"user not found";
        }
        find.setId(id);
        if(userDto.fullName()!= null &&!userDto.fullName().isBlank()){
            find.setFullName(userDto.fullName());
        }
        if(userDto.username()!= null && !userDto.username().isBlank()){
            find.setUsername(userDto.username());
        }
        if(userDto.status()!= null){
            find.setStatus(userDto.status());
        }
        if(userDto.telegramChatId()!= null){
            find.setTelegramChatId(userDto.telegramChatId());
        }
        find.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(find);
        return "you update success!!!";
    }
    public ApiResponse forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        ApiResponse apiResponse = new ApiResponse();
        UserEntity find = userRepository.findUserEntityByUsername(forgotPasswordDto.username());
        if (find == null) {
            apiResponse.setCode(CodeConstant.NOT_FOUND);
            apiResponse.setMessage(MessageConstant.NOT_FOUND);
            return apiResponse;
        }
        if (find.getTelegramChatId() == null) {
            apiResponse.setCode(CodeConstant.NOT_FOUND);
            apiResponse.setMessage("telegram chat not found");
            return apiResponse;
        }
        SentOTPRequest sendOTPRequest = new SentOTPRequest();
        sendOTPRequest.setChatId(find.getTelegramChatId());
        String otp = GlobalUtil.generateOTP(6);
        sendOTPRequest.setOtp(otp);
        find.setOneTimePassword(otp);
        find.setExpiration(new Timestamp(System.currentTimeMillis() + 120000));
        userRepository.save(find);
        return verificationService.sendOTP(sendOTPRequest);
    }
    public ApiResponse forgotPasswordVerify(ForgotPasswordVerifyDto request) {
        ApiResponse apiResponse = new ApiResponse();
        UserEntity find = userRepository.findUserEntityByUsername(request.username());
        if (find == null) {
            apiResponse.setCode(CodeConstant.NOT_FOUND);
            apiResponse.setMessage(MessageConstant.NOT_FOUND);
            return apiResponse;
        }
        if (find.getTelegramChatId() == null) {
            apiResponse.setCode(CodeConstant.NOT_FOUND);
            apiResponse.setMessage("telegram chat not found");
            return apiResponse;
        }
        if(find.getOneTimePassword()==null){
            apiResponse.setCode(CodeConstant.NOT_FOUND);
            apiResponse.setMessage(MessageConstant.NOT_FOUND);
            return apiResponse;
        }
        if(!find.getOneTimePassword().equals(request.otp())){
            apiResponse.setCode(CodeConstant.INVALID);
            apiResponse.setMessage("Worng one-time password");
            return apiResponse;
        }
        if(find.getExpiration().getTime()<System.currentTimeMillis()){
            apiResponse.setCode(CodeConstant.INVALID);
            apiResponse.setMessage("Expiration one-time password");
            return apiResponse;

        }
        find.setExpiration(new Timestamp(System.currentTimeMillis()));
        userRepository.save(find);
        apiResponse.setCode(CodeConstant.SUCCESS);
        apiResponse.setMessage(MessageConstant.SUCCESS);
        apiResponse.setData(find.getPassword());
        return apiResponse;
    }



}
