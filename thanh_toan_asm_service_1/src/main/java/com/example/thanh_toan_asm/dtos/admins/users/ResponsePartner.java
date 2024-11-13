package com.example.thanh_toan_asm.dtos.admins.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ResponsePartner {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String avatar;

}
