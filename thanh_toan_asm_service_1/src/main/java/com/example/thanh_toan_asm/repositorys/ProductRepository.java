package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.entitys.Product;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(String i, Limit limit);
}
