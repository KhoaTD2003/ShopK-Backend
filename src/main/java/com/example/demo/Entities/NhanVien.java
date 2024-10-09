package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "NhanVien")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "manv")
    private String maNV;
    @Column(name = "hoten")
    private String hoTen;
    @Temporal(TemporalType.DATE)
    private Date namSinh;
    @Column(name = "chucvu")
    private String chucVu;
    @Column(name = "diachi")
    private String diaChi;
    @Column(name = "email")
    private String email;
    @Column(name = "sdt")
    private String soDT;
    @Column(name = "trangthai")
    private String trangThai;
}
