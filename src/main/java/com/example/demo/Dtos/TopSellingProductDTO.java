package com.example.demo.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TopSellingProductDTO {

        private String tenSanPham;
        private Long soLuong;
        private BigDecimal tongTien;

        public TopSellingProductDTO(String tenSanPham, Long soLuong, BigDecimal tongTien) {
            this.tenSanPham = tenSanPham;
            this.soLuong = soLuong;
            this.tongTien = tongTien;
        }

        // Getters and setters
        public String getTenSanPham() {
            return tenSanPham;
        }

        public void setTenSanPham(String tenSanPham) {
            this.tenSanPham = tenSanPham;
        }

        public Long getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(Long soLuong) {
            this.soLuong = soLuong;
        }

        public BigDecimal getTongTien() {
            return tongTien;
        }

        public void setTongTien(BigDecimal tongTien) {
            this.tongTien = tongTien;

}}
