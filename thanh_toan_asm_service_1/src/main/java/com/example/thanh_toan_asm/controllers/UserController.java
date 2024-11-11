package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.registerUser.UserRegisterDto;
import com.example.thanh_toan_asm.dtos.registerUser.UserRegisterRespon;
import com.example.thanh_toan_asm.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "user/v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<UserRegisterRespon> register(@RequestBody UserRegisterDto userRegisterDto, HttpServletRequest request){
        return userService.registerUser(userRegisterDto);
    }

}
