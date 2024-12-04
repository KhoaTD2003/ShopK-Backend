package com.example.demo.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietHoaDonDto {

        private UUID id;
        private String maHoaDon; // Mã hóa đơn
        private String tenSanPham; // Tên sản phẩm
        private int soLuong;
        private BigDecimal donGia;
        private BigDecimal tongTien;
        private BigDecimal giamGia; // Thêm trường giảm giá vào DTO
        private BigDecimal tongTienSauGiamGia; // Tổng tiền sau giảm giá
        private String ghiChu;
        private boolean trangThai;



        // Getters và Setters

}
