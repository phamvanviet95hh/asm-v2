package com.example.thanh_toan_asm.dtos.admins.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResponProductGloableDto {

    private Boolean success;
    private String message;

}
