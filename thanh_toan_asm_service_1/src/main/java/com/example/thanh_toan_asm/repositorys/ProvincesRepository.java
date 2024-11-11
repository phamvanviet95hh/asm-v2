package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.entitys.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvincesRepository extends JpaRepository<Provinces, String> {

}
