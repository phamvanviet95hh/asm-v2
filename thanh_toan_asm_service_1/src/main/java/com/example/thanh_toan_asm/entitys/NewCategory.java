package com.example.thanh_toan_asm.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "category_new")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    @OneToMany(mappedBy = "newCategory", cascade = CascadeType.ALL)
    private Set<New> news;

}
