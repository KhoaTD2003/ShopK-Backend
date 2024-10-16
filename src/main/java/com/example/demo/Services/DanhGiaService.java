package com.example.demo.Services;

import com.example.demo.Entities.DanhGia;
import com.example.demo.Repositories.DanhGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DanhGiaService {

    @Autowired
    private DanhGiaRepository dgRepo;

    public List<DanhGia> getAll() {
        return dgRepo.findAll();
    }

    public DanhGia add(DanhGia danhGia){
        return dgRepo.save(danhGia);
    }

    public DanhGia update(UUID id, DanhGia danhGia){
        Optional<DanhGia> optionalDanhGia = dgRepo.findById(id);
        if(optionalDanhGia.isPresent()){
            DanhGia dg = optionalDanhGia.get();
            dg.setBinhLuan(danhGia.getBinhLuan());
            dg.setDiem(danhGia.getDiem());
            dg.setAnh1(danhGia.getAnh1());
            dg.setAnh2(danhGia.getAnh2());
            dg.setAnh3(danhGia.getAnh3());
            return dgRepo.save(danhGia);
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<DanhGia> optionalDanhGia = dgRepo.findById(id);
        if (optionalDanhGia.isPresent()) {
            dgRepo.delete(optionalDanhGia.get());
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }

    @Autowired
    private DanhGiaRepository danhGRepo;

    public Page<DanhGia> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return danhGRepo.findAll(pageable);
    }
}
