package com.example.demo.Dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DatDonRequest {
    private NguoiDungDto nguoiDung;
    private String maHoaDon;
    private String tongTien;
    private String tienThu;
    private String tienGiam;
    private String ghiChu;
    private String maGiamGia;
    private List<ChiTietSanPham> sanPhamList; // Danh sách sản phẩm

}
