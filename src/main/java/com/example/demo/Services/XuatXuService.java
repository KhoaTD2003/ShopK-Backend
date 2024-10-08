package com.example.demo.Services;

import com.example.demo.Entities.XuatXu;
import com.example.demo.Repositories.XuatXuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class XuatXuService {

    @Autowired
    private XuatXuRepo xuatXuRepo;

    public List<XuatXu> getAll() {
        return this.xuatXuRepo.findAll();
    }

    public XuatXu add(XuatXu xuatXu) {
        return this.xuatXuRepo.save(xuatXu);
    }

    public XuatXu update(UUID id, XuatXu xuatXudetail) {
        Optional<XuatXu> optionalXuatXu = this.xuatXuRepo.findById(id);
        if (optionalXuatXu.isPresent()) {
            XuatXu xuatXu = optionalXuatXu.get();
            xuatXu.setTen(xuatXudetail.getTen());
            xuatXu.setMa(xuatXudetail.getMa());
            return this.xuatXuRepo.save(xuatXu);
        } else {
            throw new RuntimeException("Không tìm thấy chất liệu với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<XuatXu> optionalXuatXu = this.xuatXuRepo.findById(id);
        if (optionalXuatXu.isPresent()) {
            this.xuatXuRepo.delete(optionalXuatXu.get());
        } else {
            throw new RuntimeException("Không tìm thấy chất liệu với ID: " + id);
        }

    }
}
