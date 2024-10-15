package com.example.demo.Dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DatDonRequest {
    private NguoiDungDto nguoiDung;
    private String tongTien;
    private String tienThu;
    private String tienGiam;
    private String ghiChu;
    private String maGiamGia;

}
