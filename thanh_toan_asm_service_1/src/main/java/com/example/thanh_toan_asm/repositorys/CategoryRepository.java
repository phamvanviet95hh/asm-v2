package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.entitys.NewCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<NewCategory, Long> {
    boolean existsByName(String name);

    @Query("select s from NewCategory s where s.name like LOWER(CONCAT('%', :categoryName, '%'))")
    List<NewCategory> searchCategory(String categoryName);
}
