package com.example.thanh_toan_asm.dtos.admins.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestProductCategoryDto {

    private String productCategoryName;
    private String productCategoryAlias;
    private String productCategoryDescription;
    private Long productCategoryParentId;
    private Long userId;

}
