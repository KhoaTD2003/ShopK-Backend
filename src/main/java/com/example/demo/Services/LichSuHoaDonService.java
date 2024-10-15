package com.example.demo.Services;

import com.example.demo.Entities.LichSuHoaDon;
import com.example.demo.Repositories.LichSuHoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class LichSuHoaDonService {
    @Autowired
    private LichSuHoaDonRepository lichSuHoaDonRepository;

    // Lấy tất cả lịch sử hóa đơn
    public List<LichSuHoaDon> getAll() {
        return lichSuHoaDonRepository.findAll();
    }

    // Thêm lịch sử hóa đơn mới
    public LichSuHoaDon add(LichSuHoaDon lichSuHoaDon) {
        return lichSuHoaDonRepository.save(lichSuHoaDon);
    }

    // Lấy lịch sử hóa đơn theo ID
    public LichSuHoaDon getById(UUID id) {
        return lichSuHoaDonRepository.findById(id).orElseThrow(() -> new RuntimeException("Lịch sử hóa đơn không tồn tại"));
    }

    // Cập nhật lịch sử hóa đơn
    public LichSuHoaDon update(UUID id, LichSuHoaDon lichSuHoaDonDetails) {
        LichSuHoaDon lichSuHoaDon = getById(id);
        lichSuHoaDon.setMoTa(lichSuHoaDonDetails.getMoTa());
//        lichSuHoaDon.setNgayThucHien(lichSuHoaDonDetails.getNgayThucHien());
        lichSuHoaDon.setTrangThai(lichSuHoaDonDetails.getTrangThai());
//        lichSuHoaDon.setNhanVien(lichSuHoaDonDetails.getNhanVien());
        return lichSuHoaDonRepository.save(lichSuHoaDon);
    }

    // Xóa lịch sử hóa đơn
    public void delete(UUID id) {
        LichSuHoaDon lichSuHoaDon = getById(id);
        lichSuHoaDonRepository.delete(lichSuHoaDon);
    }
    // Lấy tất cả lịch sử hóa đơn có phân trang
    public Page<LichSuHoaDon> getAll(Pageable pageable) {
        return lichSuHoaDonRepository.findAll(pageable);
    }
}
