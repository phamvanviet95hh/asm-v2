package com.example.thanh_toan_asm.dtos.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ResponseProvince {

    private String code;
    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String codeName;

}
