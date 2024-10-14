package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "DanhGia")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_sanphamct", referencedColumnName = "id")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "bihluan")
    private String binhLuan;
    @Column(name = "diem")
    private Integer diem;
    @Column(name = "anh1")
    private String anh1;
    @Column(name = "anh2")
    private String anh2;
    @Column(name = "anh3")
    private String anh3;
}
