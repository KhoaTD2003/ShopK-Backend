package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "TaiKhoan")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"matKhau"})
public class TaiKhoan {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "tentaikhoan", length = 50)
    private String tenTaiKhoan;

    @Column(name = "matkhau", length = 50)
    private String matKhau;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "trangthai")
    private boolean trangThai;

    @Column(name = "resetToken")
    private String resetToken; // Thêm trường resetToken

    public TaiKhoan(UUID idTaiKhoan) {
        this.id = idTaiKhoan;
    }

    @OneToOne(mappedBy = "taiKhoan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private NguoiDung nguoiDung;

    @Column(name = "ngaytao")
    private LocalDateTime ngayTao;
    @PrePersist
    public void prePersist() {
        if (this.ngayTao == null) {
            this.ngayTao = LocalDateTime.now(); // Thiết lập giá trị createdAt khi thêm mới
        }
    }
}
