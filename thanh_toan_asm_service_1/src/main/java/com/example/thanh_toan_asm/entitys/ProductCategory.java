package com.example.thanh_toan_asm.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.example.thanh_toan_asm.dtos.admins.products.ConvertProductDto;

@Entity
@Table(name = "productCategory")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductCategory {
    public ProductCategory(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nameCategory;
    private String description;
    private String alias;
    private String parentId;
    private String sort;
    private String home;
    private String status;
    @Column(columnDefinition = "TEXT")
    private String images;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

    @ManyToOne
    @JoinColumn(name = "user_id", unique = false)
    private UserUntity userEntity;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    private Set<Product> productSet;

}
