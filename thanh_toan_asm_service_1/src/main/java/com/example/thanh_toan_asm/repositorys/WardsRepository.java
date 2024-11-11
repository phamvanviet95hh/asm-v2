package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.entitys.Districts;
import com.example.thanh_toan_asm.entitys.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardsRepository extends JpaRepository<Ward, String> {
    List<Ward> findByDistricts(Districts districts);
}
