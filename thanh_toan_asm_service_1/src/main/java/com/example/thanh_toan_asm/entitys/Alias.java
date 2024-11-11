package com.example.thanh_toan_asm.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "alias")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Alias {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String alias;
    private String detail;
    private LocalDateTime updateAt;
    private LocalDateTime createAt;
    private String status;
}
