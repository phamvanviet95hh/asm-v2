package com.example.thanh_toan_asm.entitys;

import com.example.thanh_toan_asm.dtos.address.ResponseProvince;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@Entity
@Table(name = "provinces")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Provinces {

    @Id
    private String code;

    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String codeName;

    @ManyToOne
    @JoinColumn(name = "administrative_unit_id", nullable = true)
    private AdministrativeUnits administrativeUnits;

    @ManyToOne
    @JoinColumn(name = "administrative_region_id", nullable = true)
    private AdministrativeRegions administrativeRegions;

    @OneToMany(mappedBy = "provinces", cascade = CascadeType.ALL)
    private Set<Districts> districts;

    public Provinces(String provincesId) {
        this.code = provincesId;
    }

    public ResponseProvince getVo() {
        ResponseProvince responseProvince = new ResponseProvince();
        BeanUtils.copyProperties(this, responseProvince);
        return responseProvince;
    }
}
