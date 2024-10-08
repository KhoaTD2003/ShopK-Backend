package com.example.demo.Entities;


import jakarta.persistence.*;
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
@Entity
@Table(name = "GioHang")
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sanphamct")
    private ChiTietSanPham chiTietSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_taikhoan")
    private TaiKhoan taiKhoan;

    @Column(name = "sanpham", length = 50)
    private String sanPham;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "dongia", precision = 19, scale = 4)
    private BigDecimal donGia;

    @Column(name = "tongtien", precision = 19, scale = 4)
    private BigDecimal tongTien;


}
