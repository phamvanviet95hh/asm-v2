package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.entitys.Menu;
import com.example.thanh_toan_asm.entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
