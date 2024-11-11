package com.example.thanh_toan_asm.dtos.product;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategoryRq {
    private Long id;
    private String nameCategory;
    private String description;
    private String alias;
    private String parentId;
    private String sort;
    private String home;
    private String status;
    private String images;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
}
