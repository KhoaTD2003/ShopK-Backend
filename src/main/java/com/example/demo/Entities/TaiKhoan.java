package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"matKhau"})
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

}
