package com.example.thanh_toan_asm.dtos.logins;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerLoginDtos {
    private Long id;
    private String address;
    private String avatar;
    private LocalDateTime createAt;
    private String detail;
    private String role;
    private String status;
    private LocalDateTime updateAt;
    private String userName;
    private String fullName;
    private String phone;
    private String email;

}
