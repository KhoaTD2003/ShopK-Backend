package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})   // Bỏ qua các thuộc tính proxy
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sanpham")
    @JsonManagedReference
    private SanPham sanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_taikhoan")
    private TaiKhoan taiKhoan;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "dongia", precision = 19, scale = 4)
    private BigDecimal donGia;

    @Column(name = "tongtien", precision = 19, scale = 4)
    private BigDecimal tongTien;


}
