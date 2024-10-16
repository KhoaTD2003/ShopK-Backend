package com.example.demo.Services;

import com.example.demo.Entities.ChiTietHoaDon;
import com.example.demo.Repositories.ChiTietHoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChiTietHoaDonService {

    @Autowired
    private ChiTietHoaDonRepository chiTietHoaDonRepo;

    public List<ChiTietHoaDon> getAll() {
        return this.chiTietHoaDonRepo.findAll();
    }

    //Phân trang
    public Page<ChiTietHoaDon> getAllPaged(int page, int size) {
        return chiTietHoaDonRepo.findAll(PageRequest.of(page, size));
    }

    public ChiTietHoaDon add(ChiTietHoaDon chiTietHoaDon) {
        if (chiTietHoaDon.getDonGia() == null || chiTietHoaDon.getSoLuong() <= 0) {
            throw new IllegalArgumentException("Đơn giá và số lượng không hợp lệ");
        }

        BigDecimal tongTien = chiTietHoaDon.getDonGia().multiply(BigDecimal.valueOf(chiTietHoaDon.getSoLuong()));
        chiTietHoaDon.setTongTien(tongTien);

        return this.chiTietHoaDonRepo.save(chiTietHoaDon);
    }

    public ChiTietHoaDon update(UUID id, ChiTietHoaDon chiTietHoaDonDetails) {
        Optional<ChiTietHoaDon> optional = chiTietHoaDonRepo.findById(id);
        if (optional.isPresent()) {
            ChiTietHoaDon chiTietHoaDon = optional.get();

            if (chiTietHoaDonDetails.getDonGia() == null || chiTietHoaDonDetails.getSoLuong() <= 0) {
                throw new IllegalArgumentException("Đơn giá và số lượng phải hợp lệ");
            }

            chiTietHoaDon.setSoLuong(chiTietHoaDonDetails.getSoLuong());
            chiTietHoaDon.setDonGia(chiTietHoaDonDetails.getDonGia());

            BigDecimal tongTien = chiTietHoaDon.getDonGia().multiply(BigDecimal.valueOf(chiTietHoaDon.getSoLuong()));
            chiTietHoaDon.setTongTien(tongTien);
            chiTietHoaDon.setGhiChu(chiTietHoaDonDetails.getGhiChu());
            chiTietHoaDon.setTrangThai(chiTietHoaDonDetails.getTrangThai());

            return chiTietHoaDonRepo.save(chiTietHoaDon);
        } else {
            throw new RuntimeException("Không tìm thấy Chi tiết hóa đơn với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<ChiTietHoaDon> optional = chiTietHoaDonRepo.findById(id);
        if (optional.isPresent()) {
            chiTietHoaDonRepo.delete(optional.get());
        } else {
            throw new RuntimeException("Không tìm thấy Chi tiết hóa đơn với ID: " + id);
        }
    }
}
