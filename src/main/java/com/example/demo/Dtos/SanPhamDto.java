package com.example.demo.Dtos;

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
}
