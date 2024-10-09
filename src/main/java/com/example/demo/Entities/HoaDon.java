package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "mahoadon")
    private String maHoaDon;
    @Column(name = "tenkh")
    private String tenKH;
    @Column(name = "tennv")
    private String tenNV;
    @Column(name = "tongtien")
    private String tongTien;
    @Column(name = "tienthu")
    private String tienThu;
    @Column(name = "tienGiam")
    private String tienGiam;
    @Temporal(TemporalType.DATE)
    private Date ngayTao;
    @Column(name = "trangthai")
    private String trangThai;
    @Column(name = "ghichu")
    private String ghiChu;
//    @ManyToOne
//    @JoinColumn(name = "id_phuongthuctt")
//    private PhuongThucTt phuongThucTt;
}
