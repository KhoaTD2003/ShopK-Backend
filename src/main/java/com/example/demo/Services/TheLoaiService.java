package com.example.demo.Services;

import com.example.demo.Entities.MauSac;
import com.example.demo.Entities.Size;
import com.example.demo.Entities.TheLoai;
import com.example.demo.Repositories.TheLoaiRepo;
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
public class TheLoaiService {

    @Autowired
    private TheLoaiRepo theLoaiRepo;

    public List<TheLoai> getAll() {
        return this.theLoaiRepo.findAll();
    }

    public TheLoai add(TheLoai theLoai) {
        return this.theLoaiRepo.save(theLoai);
    }

    public Page<TheLoai> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12, Sort.by("ngayTao").descending());
        return theLoaiRepo.findAll(pageable);
    }

    public String GenarateCategoryCode() {
        // Lấy mã sản phẩm lớn nhất từ cơ sở dữ liệu
        Integer maxBrandNumber = theLoaiRepo.getMaxCategoryCode();

        // Nếu không có sản phẩm nào, bắt đầu từ SP001
        if (maxBrandNumber == null) {
            return "TL01";
        }

        // Tăng số lên 1 và tạo mã mới theo định dạng SPxxx
        int newBrandNumber = maxBrandNumber + 1;
        return String.format("TL%3d", newBrandNumber);
    }
    // Kiểm tra mã sản phẩm đã tồn tại hay chưa
    public boolean isCategoryCodeExist(String ma) {
        return theLoaiRepo.existsByMa(ma); // Kiểm tra mã sản phẩm có tồn tại trong cơ sở dữ liệu
    }

    public boolean existsByName(String name) {
        return theLoaiRepo.existsByTen(name); // Gọi repo để kiểm tra
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

    public TheLoai findById(UUID id) {
        return theLoaiRepo.findById(id).orElse(null);
    }

}
