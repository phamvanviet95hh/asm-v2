package com.example.thanh_toan_asm.dtos.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuUpdateRequest {
    private Long menuId;
    private String nameMenu;
    private String titleMenu;
    private String link;
    private String status;
    private String parentId;
}
