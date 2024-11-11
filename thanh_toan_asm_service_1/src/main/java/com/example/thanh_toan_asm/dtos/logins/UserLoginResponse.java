package com.example.thanh_toan_asm.dtos.logins;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {

    private Boolean success;
    private String message;
    private PartnerLoginDtos data;
    private String token;

}
