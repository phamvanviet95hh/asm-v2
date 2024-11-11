package com.example.thanh_toan_asm.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "new")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class New {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private String images;
    private String contents;
    private String alias;
    private String home;
    private String status;


    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;


    @ManyToOne
    @JoinColumn(name = "category_id", unique = true)
    private NewCategory newCategory;

}
