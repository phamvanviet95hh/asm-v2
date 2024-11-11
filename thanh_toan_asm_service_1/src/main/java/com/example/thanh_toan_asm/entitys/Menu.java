package com.example.thanh_toan_asm.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nameMenu;
    private String titleMenu;
    private String link;
    private String status;
    private String parentId;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

    @ManyToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserUntity userEntity;

}
