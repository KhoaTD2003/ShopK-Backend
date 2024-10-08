package com.example.demo.Services;

import com.example.demo.Entities.TheLoai;
import com.example.demo.Repositories.TheLoaiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TheLoaiService {

    @Autowired
    private TheLoaiRepo theLoaiRepo;

    public List<TheLoai> getAll() {
        return this.theLoaiRepo.findAll();
    }

    public TheLoai add(TheLoai theLoai) {
        return this.theLoaiRepo.save(theLoai);
    }

    public TheLoai update(UUID id, TheLoai theLoaidetail) {
        Optional<TheLoai> optionalTheLoai = this.theLoaiRepo.findById(id);
        if (optionalTheLoai.isPresent()) {
            TheLoai theLoai = optionalTheLoai.get();
            theLoai.setTen(theLoaidetail.getTen());
            theLoai.setMa(theLoaidetail.getMa());
            return this.theLoaiRepo.save(theLoai);
        } else {
            throw new RuntimeException("Không tìm thấy chất liệu với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<TheLoai> optionalTheLoai = this.theLoaiRepo.findById(id);
        if (optionalTheLoai.isPresent()) {
            this.theLoaiRepo.delete(optionalTheLoai.get());
        } else {
            throw new RuntimeException("Không tìm thấy chất liệu với ID: " + id);
        }

    }
}
