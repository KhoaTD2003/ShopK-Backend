package com.example.demo.Dtos;

import com.example.demo.Entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class SanPhamDto {

    private String tenSP;
    private String maSP;
    private BigDecimal giaBan;
    private String anh;
    private String mota;
    private ThuongHieu thuongHieu;
    private TheLoai theLoai;
    private Size size;
    private MauSac mauSac;
//    private ChatLieu chatLieu;
//    private XuatXu xuatXu;
}
