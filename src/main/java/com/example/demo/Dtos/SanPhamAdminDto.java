package com.example.demo.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamAdminDto {
        private UUID id;
        private String maSP;
        private String tenSP;
        private BigDecimal giaBan;
        private Integer soLuongTon;
        private String moTa;
        private String anh;
        private String thuongHieuTen;
        private String sizeTen;
        private String mauSacTen;
        private String chatLieuTen;
        private String xuatXuTen;
        private String theLoaiTen;
//        private String hangTen;
        private Boolean trangThai;
        private LocalDateTime ngaytao;
        // Constructor, getters và setters


}
