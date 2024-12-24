package com.example.demo.Dtos;

import com.example.demo.Entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SanPhamDto {
    private UUID idSP; // Thêm thuộc tính ID sản phẩm
    private String tenSP;
    private String maSP;
    private BigDecimal giaBan;
    private String anh;
    private String mota;
    private Integer stock;
    private ThuongHieu thuongHieu;
    private TheLoai theLoai;
    private Size size;
    private MauSac mauSac;
    private Boolean trangThai;

//    private ChatLieu chatLieu;
//    private XuatXu xuatXu;
}
