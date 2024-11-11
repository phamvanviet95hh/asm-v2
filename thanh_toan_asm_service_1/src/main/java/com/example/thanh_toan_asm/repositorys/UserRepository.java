package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.entitys.UserUntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserUntity, Long> {
    UserUntity findFirstByUserName(String username);
}
