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

    // Tìm mã giảm giá theo mã
    public Optional<GiamGia> getByMa(String maGiamGia) {
        return repository.findByMa(maGiamGia);
    }

    // Cập nhật số lần sử dụng
    public boolean giamSoLanSuDung(String maGiamGia) {
        Optional<GiamGia> giamGiaOptional = repository.findByMa(maGiamGia);
        if (giamGiaOptional.isPresent()) {
            GiamGia giamGia = giamGiaOptional.get();
            if (giamGia.getSoLansd() > 0) {
                giamGia.setSoLansd(giamGia.getSoLansd() - 1); // Giảm số lần sử dụng
                repository.save(giamGia);
                return true;
            }
        }
        return false; // Không thể giảm số lần sử dụng (mã hết hạn hoặc không hợp lệ)
    }

    //    public GiamGia updateSoLansd(UUID id, int newSoLansd) {
//        GiamGia giamGia = repository.findById(id).orElse(null);
//        if (giamGia != null) {
//            giamGia.setSoLansd(newSoLansd);
//            return repository.save(giamGia);
//        }
//        return null;
//    }
    public GiamGia updateSoLansd(UUID id, int newSoLansd) {
        Optional<GiamGia> giamGiaOpt = repository.findById(id);
        if (giamGiaOpt.isPresent()) {
            GiamGia giamGia = giamGiaOpt.get();
            giamGia.setSoLansd(newSoLansd);
            return repository.save(giamGia); // Lưu lại thay đổi
        }
        return null; // Trả về null nếu không tìm thấy mã giảm giá
    }
}
