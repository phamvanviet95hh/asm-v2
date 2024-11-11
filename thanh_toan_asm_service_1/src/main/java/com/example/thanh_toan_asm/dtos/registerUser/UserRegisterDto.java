package com.example.thanh_toan_asm.dtos.registerUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDto {
    private String userName;
    private String fullName;
    private String password;
    private String address;
    private String email;
    private String phone;
    private String detail;
    private String status;
    private String avatar;
    private String role;
}
