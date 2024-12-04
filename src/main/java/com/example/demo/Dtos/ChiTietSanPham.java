package com.example.demo.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ChiTietSanPham {
    private UUID idSanPham; // ID sản phẩm
    private String tenSP;
    private int soLuong; // Số lượng
    private double donGia; // Đơn giá
}
