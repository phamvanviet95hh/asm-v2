package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.email_request.EmailRequest;
import com.example.thanh_toan_asm.dtos.gtelpay_request.CreateVirtualAccountRequest;
import com.example.thanh_toan_asm.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/asm/v1")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<BaseResponse<String>> sendEmail(@RequestBody EmailRequest request) {
        return emailService.sendEmail(request);
    }
}
