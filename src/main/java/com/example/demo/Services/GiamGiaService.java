package com.example.demo.Services;

import com.example.demo.Entities.GiamGia;
import com.example.demo.Repositories.GiamGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class GiamGiaService {
    @Autowired
    private GiamGiaRepository repository;

    // Lấy tất cả giảm giá
    public List<GiamGia> getAll() {
        return repository.findAll();
    }

    // Lấy giảm giá theo ID
    public GiamGia findById(UUID id) {
        Optional<GiamGia> optional = repository.findById(id);
        return optional.orElse(null);
    }

    // Thêm giảm giá mới
    public GiamGia add(GiamGia giamGia) {
        return repository.save(giamGia);
    }

    // Cập nhật giảm giá
    public GiamGia update(UUID id, GiamGia giamGiaDetails) {
        Optional<GiamGia> optional = repository.findById(id);
        if (optional.isPresent()) {
            GiamGia existingGiamGia = optional.get();
            existingGiamGia.setMa(giamGiaDetails.getMa());
            existingGiamGia.setTen(giamGiaDetails.getTen());
            existingGiamGia.setNgayBatDau(giamGiaDetails.getNgayBatDau());
            existingGiamGia.setNgayKetThuc(giamGiaDetails.getNgayKetThuc());
            existingGiamGia.setTrangThai(giamGiaDetails.getTrangThai());
            return repository.save(existingGiamGia);
        }
        throw new RuntimeException("Không tìm thấy giảm giá");
    }

    // Xóa giảm giá
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
