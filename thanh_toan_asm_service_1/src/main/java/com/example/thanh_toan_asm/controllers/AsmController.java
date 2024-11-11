package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.CreateVAResponse;
import com.example.thanh_toan_asm.dtos.gtelpay_request.CreateVirtualAccountRequest;
import com.example.thanh_toan_asm.dtos.gtelpay_response.create_token.CreateTokenResponse;
import com.example.thanh_toan_asm.dtos.gtelpay_response.create_va.CreateVirtualAccountResponse;
import com.example.thanh_toan_asm.services.AsmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/asm/v1")
public class AsmController {

    @Autowired
    private AsmService service;
    @PostMapping(value = "/test")
    public String test() {

        return "sss";
    }

//    @PostMapping(value = "/create/token")
//    public ResponseEntity<BaseResponse<CreateTokenResponse>> createAuthenticationToken(@RequestParam("code") String merchantCode, @RequestParam("pass") String password,@RequestParam("id") Long userId) {
//        return service.createAuthenticationToken(merchantCode,password,userId);
//    }

    @PostMapping(value = "/create/virtual/acc")
    public ResponseEntity<CreateVAResponse<CreateVirtualAccountResponse>> createVirtualAccount(@RequestBody CreateVirtualAccountRequest request) throws Exception {
        System.out.println(request);
        return service.createVirtualAccount(request);
    }

    @PostMapping(value = "/update/virtual/code")
    public ResponseEntity<BaseResponse<String>> updateVirtualAccount(@RequestParam("code") String merchantCode,@RequestParam("pass") String password) {

        return service.updateVirtualAccount(merchantCode,password,"");
    }

    @PostMapping(value = "/get/virtual/acc")
    public ResponseEntity<BaseResponse<String>> getDetailVirtualAccount(@RequestParam("code") String merchantCode,@RequestParam("pass") String password) {

        return service.getDetailVirtualAccount(merchantCode,password,"");
    }

    @PostMapping(value = "/close/virtual")
    public ResponseEntity<BaseResponse<String>> closeVirtualAccount(@RequestParam("code") String merchantCode,@RequestParam("pass") String password) {

        return service.closeVirtualAccount(merchantCode,password,"");
    }

    @PostMapping(value = "/reopen/virtual")
    public ResponseEntity<BaseResponse<String>> reopenVirtualAccount(@RequestParam("code") String merchantCode,@RequestParam("pass") String password) {

        return service.reopenVirtualAccount(merchantCode,password,"");
    }
}
