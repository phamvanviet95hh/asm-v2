package com.example.thanh_toan_asm.dtos.admins.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCheckPassword {
    private Long userId;
    private String password;
    private String newPassword;
}
