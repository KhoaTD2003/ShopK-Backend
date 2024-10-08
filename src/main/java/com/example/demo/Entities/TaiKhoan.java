package com.example.demo.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "TaiKhoan")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaiKhoan {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_nhanvien", referencedColumnName = "id")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_khachhang", referencedColumnName = "id")
    private KhachHang khachHang;

    @Column(name = "tentaikhoan", length = 50)
    private String tenTaiKhoan;

    @Column(name = "matkhau", length = 50)
    private String matKhau;

    @Column(name = "trangthai")
    private boolean trangThai;


}
