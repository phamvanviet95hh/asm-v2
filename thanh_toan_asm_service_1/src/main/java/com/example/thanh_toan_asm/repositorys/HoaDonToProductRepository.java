package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.entitys.HoadonToProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonToProductRepository extends JpaRepository<HoadonToProduct, Long> {
}
