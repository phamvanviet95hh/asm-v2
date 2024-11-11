package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.dtos.OrederConvert;
import com.example.thanh_toan_asm.entitys.Order;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.entitys.UserUntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserEntity(UserUntity userId);

    Order getByStatusAndUserEntityAndProductAndNameProduct(String status, UserUntity userUntity, Product product, String nameProduct);
}
