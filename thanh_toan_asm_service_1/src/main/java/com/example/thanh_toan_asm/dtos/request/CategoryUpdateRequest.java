package com.example.thanh_toan_asm.dtos.request;

import com.example.thanh_toan_asm.entitys.New;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateRequest {
    private String name;
    private String alias;
    private String description;
    private String images;
    private String parentId;
    private String home;
    private String sort;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
}
