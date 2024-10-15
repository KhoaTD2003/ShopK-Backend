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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GiamGia")
public class GiamGia {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "ma", nullable = true)
    private String ma;

    @Column(name = "ten", nullable = true)
    private String ten;

    @Column(name = "ngaybatdau", nullable = true)
    private Date ngayBatDau;

    @Column(name = "ngayketthuc", nullable = true)
    private Date ngayKetThuc;

    @Column(name = "giamgia")
    private String giamGia;

    @Column(name = "soLanSuDung")
    private Integer soLansd;

    @Column(name = "trangthai", nullable = true)
    private Boolean trangThai;
}
