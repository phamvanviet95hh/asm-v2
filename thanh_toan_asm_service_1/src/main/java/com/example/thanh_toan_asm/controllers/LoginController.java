package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.logins.UserLoginRequest;
import com.example.thanh_toan_asm.dtos.logins.UserLoginResponse;
import com.example.thanh_toan_asm.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController

public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest userLoginRequest){
        return loginService.login(userLoginRequest);
    }

}
