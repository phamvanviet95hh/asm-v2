package com.example.thanh_toan_asm.dtos.admins.products;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConvertProductDto {

    private Long id;
    private String productName;
    private String productDescription;
    private Long productPrice;
    private Long productOldPrice;
    private Long productSalePrice;
    private String productImage;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
    private String userName;
    private String categoryName;
    private String link;
}
