package com.example.thanh_toan_asm.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserUntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserUntity(Long id) {
        this.id = id;
    }

    @Column(unique = true)
    private String userName;

    private String fullName;
    private String password;
    private String address;
    private String email;
    private String phone;
    private String detail;
    private String status;

    @Column(columnDefinition = "TEXT")
    private String avatar;
    private String role;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<Product> productSet;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<Menu> menus;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<ProductCategory> productCategories;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<Order> orders;

}
