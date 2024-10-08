package com.example.demo.Services;



import com.example.demo.Entities.GioHang;
import com.example.demo.Repositories.GioHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

    @Service
    public class GioHangService {

        @Autowired
        private GioHangRepository repository;

        // Lấy tất cả giỏ hàng
        public List<GioHang> getAll() {
            return repository.findAll();
        }

        // Lấy giỏ hàng theo ID
        public GioHang findById(UUID id) {
            Optional<GioHang> optionalGioHang = repository.findById(id);
            if (optionalGioHang.isPresent()) {
                return optionalGioHang.get();
            } else {
                throw new RuntimeException("Không tìm thấy giỏ hàng với ID: " + id);
            }
        }

        // Thêm giỏ hàng mới và tính tổng tiền (dongia * soluong)
        public GioHang add(GioHang gioHang) {
            // Kiểm tra donGia và soLuong trước khi tính tổng tiền
            if (gioHang.getDonGia() == null || gioHang.getSoLuong() <= 0) {
                throw new IllegalArgumentException("Đơn giá và số lượng phải hợp lệ");
            }

            // Tính tổng tiền trước khi lưu
            BigDecimal tongTien = gioHang.getDonGia().multiply(BigDecimal.valueOf(gioHang.getSoLuong()));
            gioHang.setTongTien(tongTien);

            return repository.save(gioHang);
        }

        // Cập nhật giỏ hàng và tính tổng tiền (dongia * soluong)
        public GioHang update(UUID id, GioHang gioHangDetails) {
            Optional<GioHang> optionalGioHang = repository.findById(id);
            if (optionalGioHang.isPresent()) {
                GioHang gioHang = optionalGioHang.get();

                // Kiểm tra giá trị donGia và soLuong trước khi cập nhật
                if (gioHangDetails.getDonGia() == null || gioHangDetails.getSoLuong() <= 0) {
                    throw new IllegalArgumentException("Đơn giá và số lượng phải hợp lệ");
                }

                // Cập nhật các thuộc tính giỏ hàng
                gioHang.setSanPham(gioHangDetails.getSanPham());  // Cập nhật tên sản phẩm
                gioHang.setSoLuong(gioHangDetails.getSoLuong());  // Cập nhật số lượng
                gioHang.setDonGia(gioHangDetails.getDonGia());    // Cập nhật đơn giá

                // Tính lại tổng tiền sau khi cập nhật
                BigDecimal tongTien = gioHang.getDonGia().multiply(BigDecimal.valueOf(gioHang.getSoLuong()));
                gioHang.setTongTien(tongTien);  // Cập nhật tổng tiền

                return repository.save(gioHang);
            } else {
                throw new RuntimeException("Không tìm thấy giỏ hàng với ID: " + id);
            }
        }

        // Xóa giỏ hàng theo ID
        public void delete(UUID id) {
            Optional<GioHang> optionalGioHang = repository.findById(id);
            if (optionalGioHang.isPresent()) {
                repository.delete(optionalGioHang.get());
            } else {
                throw new RuntimeException("Không tìm thấy giỏ hàng với ID: " + id);
            }
        }
    }


