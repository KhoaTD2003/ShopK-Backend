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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TaiKhoan {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "tentaikhoan", length = 50)
    private String tenTaiKhoan;

    @Column(name = "matkhau", length = 50)
    private String matKhau;

    @Column(name = "role")
    private String roLe;

    @Column(name = "trangthai")
    private boolean trangThai;


}
