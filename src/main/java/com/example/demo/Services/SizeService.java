package com.example.demo.Services;

import com.example.demo.Entities.MauSac;
import com.example.demo.Entities.Size;
import com.example.demo.Entities.XuatXu;
import com.example.demo.Repositories.SizeRepository;
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
public class SizeService {
    @Autowired
    private SizeRepository sRepo;

    public List<Size> getAll() {
        return sRepo.findAll();
    }

    public Page<Size> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12, Sort.by("ngayTao").descending());
        return sRepo.findAll(pageable);
    }

    public String GenarateSizeCode() {
        // Lấy mã sản phẩm lớn nhất từ cơ sở dữ liệu
        Integer maxBrandNumber = sRepo.getMaxSizeCode();

        // Nếu không có sản phẩm nào, bắt đầu từ SP001
        if (maxBrandNumber == null) {
            return "S01";
        }

        // Tăng số lên 1 và tạo mã mới theo định dạng SPxxx
        int newBrandNumber = maxBrandNumber + 1;
        return String.format("S%3d", newBrandNumber);
    }
    // Kiểm tra mã sản phẩm đã tồn tại hay chưa
    public boolean isSizeCodeExist(String ma) {
        return sRepo.existsByMa(ma); // Kiểm tra mã sản phẩm có tồn tại trong cơ sở dữ liệu
    }

    public boolean existsByName(String name) {
        return sRepo.existsByTen(name); // Gọi repo để kiểm tra
    }

    public Size add(Size size){
        return sRepo.save(size);
    }

    public Size update(UUID id, Size size){
        Optional<Size> optionalSize = sRepo.findById(id);
        if(optionalSize.isPresent()){
            Size s = optionalSize.get();
            s.setMa(size.getMa());
            s.setTen(size.getTen());

            return sRepo.save(size);
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<Size> optionalSize = sRepo.findById(id);
        if(optionalSize.isPresent()){
            sRepo.delete(optionalSize.get());
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }

    public Size findById(UUID id) {
        return sRepo.findById(id).orElse(null);
    }
}
