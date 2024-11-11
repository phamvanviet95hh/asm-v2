package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.entitys.Alias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AliasRepository extends JpaRepository<Alias, Long> {
    boolean existsByAlias(String aliasName);
}
