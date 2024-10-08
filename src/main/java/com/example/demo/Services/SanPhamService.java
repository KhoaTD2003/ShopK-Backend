package com.example.demo.Services;

import com.example.demo.Entities.SanPham;
import com.example.demo.Repositories.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SanPhamService {
    @Autowired
    private SanPhamRepository spRepo;

    public List<SanPham> getAll() {
        return spRepo.findAll();
    }

    public SanPham add(SanPham sanPham){
        return spRepo.save(sanPham);
    }


    public SanPham update(UUID id, SanPham sanPham){
        Optional<SanPham> optionalSanPham= spRepo.findById(id);
        if(optionalSanPham.isPresent()){
            SanPham sp = optionalSanPham.get();
            sp.setTen(sanPham.getTen());
            sp.setMaSP(sanPham.getMaSP());
            return spRepo.save(sanPham);
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<SanPham> optionalSanPham = spRepo.findById(id);
        if(optionalSanPham.isPresent()){
            spRepo.delete(optionalSanPham.get());
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }
}
