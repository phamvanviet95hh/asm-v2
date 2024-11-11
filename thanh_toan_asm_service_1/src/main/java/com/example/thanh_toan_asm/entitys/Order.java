package com.example.thanh_toan_asm.entitys;

import com.example.thanh_toan_asm.dtos.OrederConvert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;
    private String status;
    private String code;
    private String nameProduct;
    private String typeOfTransport;
    private int qty;
    private Long price;
    private Long totalPrice;
    private String infoTransactions;
    private String link;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserUntity userEntity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public OrederConvert getVo() {
        OrederConvert orederConvert = new OrederConvert();
        BeanUtils.copyProperties(this, orederConvert);
        orederConvert.setUserEntity(userEntity.getId());
        orederConvert.setProduct(product.getId());
        return orederConvert;
    }
}
