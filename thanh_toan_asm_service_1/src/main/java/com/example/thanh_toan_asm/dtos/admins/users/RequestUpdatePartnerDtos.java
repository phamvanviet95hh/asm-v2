package com.example.thanh_toan_asm.dtos.admins.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RequestUpdatePartnerDtos {
    private Long id;
    private String fullName;
    private String userName;
    private String address;
    private String email;
    private String phone;
}

