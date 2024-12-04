package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "ChiTietHoaDon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietHoaDon {
//    @Id
//    @GeneratedValue
//    private UUID id; // ID chi tiết hóa đơn
//
//    @Column(name = "id_hoadon", nullable = false)
//    private UUID idHoaDon; // ID hóa đơn
//
//    @Column(name = "id_sanpham", nullable = false)
//    private UUID idSanPham; // ID sản phẩm
//
//    @Column(name = "soluong", nullable = false)
//    private int soLuong; // Số lượng
//
//    @Column(name = "dongia", nullable = false)
//    private double donGia; // Đơn giá
//
//    @Column(name = "tongtien", nullable = false)
//    private double tongTien; // Tổng tiền

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id; // ID chi tiết hóa đơn

    @ManyToOne // Mối quan hệ nhiều đến một với HoaDon
    @JoinColumn(name = "id_hoadon", nullable = false)
    private HoaDon hoaDon; // Tham chiếu đến hóa đơn

    @ManyToOne // Mối quan hệ nhiều đến một với SanPham
    @JoinColumn(name = "id_sanpham", nullable = false)
    private SanPham sanPham; // Tham chiếu đến sản phẩm

    @Column(name = "soluong", nullable = false)
    private int soLuong; // Số lượng

    @Column(name = "dongia", nullable = false)
    private BigDecimal donGia; // Đơn giá

    @Column(name = "tongtien", nullable = false)
    private BigDecimal tongTien; // Tổng tiền

    @Column(name = "trangthai", nullable = false)
    private Boolean trangThai; // Tổng tiền

    @Column(name = "ghichu", nullable = false)
    private String ghiChu; // Tổng tiền
}
