package com.example.demo.Services;

import com.example.demo.Entities.Hang;
import com.example.demo.Repositories.HangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HangService {
    @Autowired
    private HangRepository hangRepository;

    public List<Hang> getAll() {
        return this.hangRepository.findAll();
    }

    public Hang add(Hang hang) {
        return this.hangRepository.save(hang);
    }

    public Hang update(UUID id, Hang hangdetail) {
        Optional<Hang> optionalHang = this.hangRepository.findById(id);
        if (optionalHang.isPresent()) {
            Hang hang = optionalHang.get();
            hang.setTen(hangdetail.getTen());
            hang.setMa(hangdetail.getMa());
            return this.hangRepository.save(hang);
        } else {
            throw new RuntimeException("Không tìm thấy chất liệu với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<Hang> optionalHang = this.hangRepository.findById(id);
        if (optionalHang.isPresent()) {
            this.hangRepository.delete(optionalHang.get());
        } else {
            throw new RuntimeException("Không tìm thấy chất liệu với ID: " + id);
        }

    }
}
