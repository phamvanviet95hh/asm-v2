package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.entitys.UserBearerToken;
import com.example.thanh_toan_asm.entitys.UserUntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBearerTokenRepository extends JpaRepository<UserBearerToken, Long> {

}
