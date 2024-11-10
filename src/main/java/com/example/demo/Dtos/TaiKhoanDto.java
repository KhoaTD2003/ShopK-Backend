package com.example.demo.Dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TaiKhoanDto {

    private String tenTaiKhoan;

    private String matKhau;

    private String sdt;

    private String email;

    private String role;
//    private Boolean trangThai;

}
