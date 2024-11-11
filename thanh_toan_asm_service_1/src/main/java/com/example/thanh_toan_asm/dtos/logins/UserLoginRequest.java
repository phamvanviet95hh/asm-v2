package com.example.thanh_toan_asm.dtos.logins;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {

    private String username;
    private String password;

}
