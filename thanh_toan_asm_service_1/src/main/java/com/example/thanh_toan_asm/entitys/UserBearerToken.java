package com.example.thanh_toan_asm.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_bearer_token")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserBearerToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime expired_at;
}
