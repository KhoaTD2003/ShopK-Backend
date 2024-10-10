package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "SanPham")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_size", referencedColumnName = "id")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "id_mausac", referencedColumnName = "id")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_chatlieu", referencedColumnName = "id")
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "id_xuatxu", referencedColumnName = "id")
    private XuatXu xuatXu;

    @ManyToOne
    @JoinColumn(name = "id_theloai", referencedColumnName = "id")
    private TheLoai theLoai;

    @ManyToOne
    @JoinColumn(name = "id_hang", referencedColumnName = "id")
    private Hang hang;

    @Column(name = "masanpham")
    private String maSP;
    @Column(name = "giaban")
    private BigDecimal giaBan;
    @Column(name = "gianhap")
    private BigDecimal giaNhap;
    @Column(name = "soluongton")
    private Integer soLuongTon;
    @Column(name = "mota")
    private String moTa;
    @Column(name = "anh")
    private String anh;
    @Column(name = "trangthai")
    private Integer trangThai;

//    @OneToMany(mappedBy = "sanPham")
//    @JsonManagedReference // Điều khiển việc tuần tự hóa
//    private List<GioHang> gioHang;
}
