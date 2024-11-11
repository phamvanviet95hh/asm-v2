package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.entitys.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
