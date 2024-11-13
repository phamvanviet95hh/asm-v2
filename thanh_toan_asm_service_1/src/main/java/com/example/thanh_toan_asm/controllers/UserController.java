package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.GloableResponse;
import com.example.thanh_toan_asm.dtos.admins.users.RequestCheckPassword;
import com.example.thanh_toan_asm.dtos.admins.users.ResponsePartner;
import com.example.thanh_toan_asm.dtos.registerUser.UserRegisterDto;
import com.example.thanh_toan_asm.dtos.registerUser.UserRegisterRespon;
import com.example.thanh_toan_asm.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "user/v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<UserRegisterRespon> register(@RequestBody UserRegisterDto userRegisterDto, HttpServletRequest request){
        return userService.registerUser(userRegisterDto);
    }

    @PostMapping(value = "checkPassword")
    public ResponseEntity<GloableResponse<RequestCheckPassword>> checkPassword(@RequestBody RequestCheckPassword checkPassword, HttpServletRequest request){
        return userService.checkPassword(checkPassword);
    }

    @PostMapping("updatePartner")
    public ResponseEntity<GloableResponse<ResponsePartner>> updatePartner(@RequestParam("data") String data,
                                                                          @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        System.out.println(data);
        return  userService.updatePartner(data, file);
    }

}
