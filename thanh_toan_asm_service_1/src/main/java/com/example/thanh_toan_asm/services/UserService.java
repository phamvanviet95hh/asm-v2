package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.dtos.registerUser.UserRegisterDto;
import com.example.thanh_toan_asm.dtos.registerUser.UserRegisterRespon;
import com.example.thanh_toan_asm.entitys.UserUntity;
import com.example.thanh_toan_asm.enums.RoleEnum;
import com.example.thanh_toan_asm.repositorys.UserRepository;
import com.google.gson.Gson;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserService {
    private Boolean success;
    private String message;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    int statusCode = 0;
    public ResponseEntity<UserRegisterRespon> registerUser(UserRegisterDto userRegisterDto){

                UserUntity register = userRepository.findFirstByUserName(userRegisterDto.getUserName());
                if (register == null) {
                    success = true;
                    message = "Insert data success!!!";
                    register = UserUntity.builder()
                            .address(userRegisterDto.getAddress())
                            .updateAt(LocalDateTime.now())
                            .avatar(userRegisterDto.getAvatar())
                            .userName(userRegisterDto.getUserName())
                            .createAt(LocalDateTime.now())
                            .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                            .role(userRegisterDto.getRole())
                            .status(userRegisterDto.getStatus())
                            .fullName(userRegisterDto.getFullName())
                            .build();
                    UserUntity userEntity = userRepository.save(register);
                    System.out.println(userEntity.getId());
                    statusCode = HttpStatus.CREATED.value();
                    success = true;
                    message = "Register Success !!!";
                }else {
                    success = false;
                    message = "Insert data Fail, User already exists!!!";
                    statusCode = HttpStatus.BAD_REQUEST.value();
                }

        return new ResponseEntity<>(UserRegisterRespon.builder()
                .success(success)
                .message(message)
                .build(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }


}
