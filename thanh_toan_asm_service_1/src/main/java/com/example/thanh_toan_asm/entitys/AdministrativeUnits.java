package com.example.thanh_toan_asm.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "administrative_units")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdministrativeUnits {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String shortName;
    private String shortNameEn;
    private String codeName;
    private String codeNameEn;
    private String fullName;
    private String fullNameEn;

    @OneToMany(mappedBy = "administrativeUnits", cascade = CascadeType.ALL)
    private Set<Provinces> provinces;

    @OneToMany(mappedBy = "administrativeUnits", cascade = CascadeType.ALL)
    private Set<Districts> districts;

    @OneToMany(mappedBy = "administrativeUnits", cascade = CascadeType.ALL)
    private Set<Ward> wards;

}
