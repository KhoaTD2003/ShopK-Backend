package com.example.demo.Services;

import com.example.demo.Entities.PhuongThucTt;
import com.example.demo.Repositories.PhuongThucThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PhuongThucThanhToanService {
    @Autowired
    private PhuongThucThanhToanRepository repository;

    // lấy tất cả các phương thức thanh toán
    public List<PhuongThucTt> GetALL() {
        return repository.findAll();
    }

    // lấy phương thức thanh toán theo ID
    public PhuongThucTt findByID(UUID id) {
        Optional<PhuongThucTt> optional = repository.findById(id);
        return optional.orElse(null);
    }

    // thêm phương thức thanh toán mới
    public PhuongThucTt add(PhuongThucTt phuongThucTt) {
        return repository.save(phuongThucTt);
    }

    // cập nhập phương thức thanh toán
    public PhuongThucTt update(UUID id, PhuongThucTt phuongThucTtDetail) {
        Optional<PhuongThucTt> optional = repository.findById(id);
        if (optional.isPresent()) {
            PhuongThucTt existingPhuongThuc = optional.get();
            existingPhuongThuc.setPhuongThucThanhToan(phuongThucTtDetail.getPhuongThucThanhToan());
            existingPhuongThuc.setMoTa(phuongThucTtDetail.getMoTa());
            return repository.save(existingPhuongThuc);
        } else {
            throw new RuntimeException("không tìm thấy phương thức thanh toán ");
        }
    }

    // xóa phương thức thanh toán
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
