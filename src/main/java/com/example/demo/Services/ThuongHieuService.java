package com.example.demo.Services;

import com.example.demo.Entities.TheLoai;
import com.example.demo.Entities.ThuongHieu;
import com.example.demo.Repositories.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuRepository.findAll();
    }

    public ThuongHieu getThuongHieuById(UUID id) {
        return thuongHieuRepository.findById(id).orElse(null);
    }

    public ThuongHieu update(UUID id, ThuongHieu thuongHieued) {
        Optional<ThuongHieu> optionalThuongHieu = this.thuongHieuRepository.findById(id);
        if (optionalThuongHieu.isPresent()) {
            ThuongHieu thuongHieu = optionalThuongHieu.get();
            thuongHieu.setTen(thuongHieued.getTen());
            thuongHieu.setMa(thuongHieued.getMa());
            return this.thuongHieuRepository.save(thuongHieu);
        } else {
            throw new RuntimeException("Không tìm thấy thuong hieu với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<ThuongHieu> optionalThuongHieu = this.thuongHieuRepository.findById(id);
        if (optionalThuongHieu.isPresent()) {
            this.thuongHieuRepository.delete(optionalThuongHieu.get());
        } else {
            throw new RuntimeException("Không tìm thấy thuong hieu với ID: " + id);
        }

    }
    public ThuongHieu add(ThuongHieu thuongHieu) {
        return thuongHieuRepository.save(thuongHieu);
    }
}
