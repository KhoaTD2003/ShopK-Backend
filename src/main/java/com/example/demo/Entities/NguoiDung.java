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
@Table(name = "NguoiDung")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "manguoidung", unique = true, nullable = false)
    private String maNguoiDung;

    @ManyToOne(fetch = FetchType.LAZY) // Tham chiếu đến bảng TaiKhoan
    @JoinColumn(name = "id_taikhoan")
    private TaiKhoan taiKhoan;

    @Column(name = "hoten")
    private String hoTen;

    @Column(name = "namsinh")
    @Temporal(TemporalType.DATE)
    private Date namSinh;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt", length = 10)
    private String sdt;

    @Column(name = "trangthai")
    private Boolean trangThai;
}
