package com.example.demo.Services;

import com.example.demo.Entities.MauSac;
import com.example.demo.Entities.ThuongHieu;
import com.example.demo.Entities.XuatXu;
import com.example.demo.Repositories.XuatXuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

//    public Page<XuatXu> getAll(int pageNumber) {
//        Pageable pageable = PageRequest.of(pageNumber, 12, Sort.by("ngayTao").descending());
//        return xuatXuRepo.findAll(pageable);
//    }

    public Page<XuatXu> getAll(int pageNumber, String ten) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("ngayTao").descending());
        if (ten != null && !ten.isEmpty()) {
            return xuatXuRepo.findByTenContaining(ten, pageable);
        }
        return xuatXuRepo.findAll(pageable);

    }

    public String GenarateOriginCode() {
        // Lấy mã sản phẩm lớn nhất từ cơ sở dữ liệu
        Integer maxBrandNumber = xuatXuRepo.getMaxOriginCode();

        // Nếu không có sản phẩm nào, bắt đầu từ SP001
        if (maxBrandNumber == null) {
            return "CL01";
        }

        // Tăng số lên 1 và tạo mã mới theo định dạng SPxxx
        int newBrandNumber = maxBrandNumber + 1;
        return String.format("CL%3d", newBrandNumber);
    }
    // Kiểm tra mã sản phẩm đã tồn tại hay chưa
    public boolean isOriginCodeExist(String ma) {
        return xuatXuRepo.existsByMa(ma); // Kiểm tra mã sản phẩm có tồn tại trong cơ sở dữ liệu
    }

    public boolean existsByName(String name) {
        return xuatXuRepo.existsByTen(name); // Gọi repo để kiểm tra
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

    public XuatXu findById(UUID id) {
        return xuatXuRepo.findById(id).orElse(null);
    }
}
