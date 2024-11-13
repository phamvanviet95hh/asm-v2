package com.example.thanh_toan_asm.repositorys;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.OrederConvert;
import com.example.thanh_toan_asm.dtos.hoadon.AdminCustomHoaDonDetailDto;
import com.example.thanh_toan_asm.dtos.hoadon.AdminCustomHoadonDto;
import com.example.thanh_toan_asm.dtos.hoadon.HoaDonRq;
import com.example.thanh_toan_asm.dtos.request.OrderCreationRequest;
import com.example.thanh_toan_asm.entitys.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    HoaDon getByCode(String code);

    @Query("select new com.example.thanh_toan_asm.dtos.hoadon.AdminCustomHoadonDto(htp.hoadon.id as idBill, " +
            " htp.hoadon.address as addressBill, htp.hoadon.code as codeBill," +
            " htp.hoadon.email as  emailBill, htp.hoadon.phone as phoneBill, " +
            " htp.hoadon.fullName as namePersionBill, htp.hoadon.note as noteBill," +
            " htp.hoadon.status as statusBill, htp.hoadon.totalPrice as totalPriceBill," +
            " htp.hoadon.qty as qtyBill, htp.product.productName as nameProduct, htp.product.productImage as imgProduct, htp.hoadon.createAt as timeCreatedAt) from HoadonToProduct htp " +
            " where (:codeHd IS NULL OR htp.hoadon.code LIKE %:codeHd%) and (:statusHd IS NULL OR htp.hoadon.status LIKE %:statusHd%) and htp.createdAt between :startDate and :endDate group by " +
            " htp.hoadon.id, htp.hoadon.address, htp.hoadon.code, htp.hoadon.email, htp.hoadon.phone, htp.hoadon.fullName, htp.hoadon.note," +
            " htp.hoadon.status, htp.hoadon.totalPrice, htp.hoadon.qty, htp.product.productName, htp.product.productImage, htp.hoadon.createAt " +
            " order by htp.hoadon.createAt desc")
    Page<AdminCustomHoadonDto> findAllCreatedAt(LocalDateTime startDate, LocalDateTime endDate, String codeHd, String statusHd, Pageable pageable);


    @Query("select new com.example.thanh_toan_asm.dtos.hoadon.AdminCustomHoaDonDetailDto( hd.id as idBill, hd.address as addressBill, " +
            " hd.code as codeBill, hd.email as emailBill, hd.phone as phoneBill, hd.fullName as namePersionBill, hd.note as noteBill," +
            " hd.status as statusBill, hd.createAt as timeCreatedAt)" +
            " from HoaDon hd where hd.id = :idHd ")
    AdminCustomHoaDonDetailDto findByCustomDetail(Long idHd);


    @Query("select new com.example.thanh_toan_asm.dtos.hoadon.AdminCustomHoadonDto(htp.hoadon.id as idBill, " +
            " htp.hoadon.address as addressBill, htp.hoadon.code as codeBill," +
            " htp.hoadon.email as  emailBill, htp.hoadon.phone as phoneBill, " +
            " htp.hoadon.fullName as namePersionBill, htp.hoadon.note as noteBill," +
            " htp.hoadon.status as statusBill, htp.hoadon.totalPrice as totalPriceBill," +
            " htp.hoadon.qty as qtyBill, htp.product.productName as nameProduct, htp.product.productImage as imgProduct, htp.hoadon.createAt as timeCreatedAt) " +
            " from HoadonToProduct htp " +
            " where htp.hoadon.id = :idHd group by " +
            " htp.hoadon.id, htp.hoadon.address, htp.hoadon.code, htp.hoadon.email, htp.hoadon.phone, htp.hoadon.fullName, htp.hoadon.note," +
            " htp.hoadon.status, htp.hoadon.totalPrice, htp.hoadon.qty, htp.product.productName, htp.product.productImage, htp.hoadon.createAt")
    List<AdminCustomHoadonDto> findByCustomDetailProduct(Long idHd);

    @Modifying
    @Transactional
    @Query("delete from HoadonToProduct htp where htp.hoadon.id = :idDd")
    void customDeleteHdToProduct(Long idDd);

    @Modifying
    @Transactional
    @Query("delete from HoaDon h where h.id = :idDd")
    void customDeleteHd(Long idDd);


    @Query("select sum(CAST(hd.totalPrice AS long)) from HoaDon hd where hd.status='2' and hd.createAt between :startOfMonth and :endOfMonth")
    Long customCountDtMonth(LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    @Query("select sum(CAST(hd.totalPrice AS long)) from HoaDon hd where hd.status='2' and hd.createAt between :startOfMonth and :endOfMonth")
    Long customCountDtDay(LocalDateTime startOfMonth, LocalDateTime endOfMonth);
}
