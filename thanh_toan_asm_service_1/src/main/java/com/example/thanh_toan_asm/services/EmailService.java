 package com.example.thanh_toan_asm.services;

 import com.example.thanh_toan_asm.dtos.BaseResponse;
 import com.example.thanh_toan_asm.dtos.GlobalValue;
 import com.example.thanh_toan_asm.dtos.email_request.EmailRequest;
 import
 com.example.thanh_toan_asm.dtos.gtelpay_response.create_va.CreateVirtualAccountResponse;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.HttpStatusCode;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Service;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.mail.SimpleMailMessage;
 import org.springframework.mail.javamail.JavaMailSender;
 import org.springframework.stereotype.Service;

 import java.io.IOException;

 @Service
 @Slf4j
 public class EmailService {
 @Autowired
 private JavaMailSender mailSender;

 private Boolean success;
 private String mailMessage;
 int statusCode = 0;

 public ResponseEntity<BaseResponse<String>> sendEmail(EmailRequest
 emailRequest) {
 try {
 SimpleMailMessage message = new SimpleMailMessage();
 message.setTo(emailRequest.getCustomerEmail());
 message.setCc(emailRequest.getCcEmail());
 message.setSubject("Thư cảm ơn khách hàng");
 message.setText(String.format(GlobalValue.emailText,
 emailRequest.getCustomerEmail(),
 emailRequest.getOrderCode(),
         emailRequest.getNameKh(),
         emailRequest.getPhoneKh(),
         emailRequest.getAddress(),
         emailRequest.getDateOfPurchase(),
         emailRequest.getProduct(),
         emailRequest.getStatus(),
         emailRequest.getTotalMoney()
 ));
 message.setFrom("asmbca@gtelcds.vn");

 mailSender.send(message);
 success = true;
 mailMessage = "sendEmail success!!!";
 statusCode = HttpStatus.OK.value();
 } catch (Exception e) {
 success = false;
 mailMessage = "sendEmail Fail!!!";
 statusCode = HttpStatus.BAD_REQUEST.value();
 e.printStackTrace();
 }
 return new ResponseEntity<>(
 new BaseResponse(success, mailMessage,null) ,
 HttpStatusCode.valueOf(statusCode));

 }
 }
