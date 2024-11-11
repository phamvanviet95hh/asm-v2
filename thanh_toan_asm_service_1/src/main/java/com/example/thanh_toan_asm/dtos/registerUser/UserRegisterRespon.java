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
public class UserRegisterRespon {
    private Boolean success;
    private String message;
}
