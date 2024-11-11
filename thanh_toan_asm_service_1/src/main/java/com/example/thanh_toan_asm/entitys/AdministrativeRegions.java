package com.example.thanh_toan_asm.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "administrative_regions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdministrativeRegions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String nameEn;
    private String codeName;
    private String codeNameEn;

    @OneToMany(mappedBy = "administrativeRegions", cascade = CascadeType.ALL)
    private Set<Provinces> provinces;



}
