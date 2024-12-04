package com.example.demo.Services;

import com.example.demo.Dtos.SanPhamDto;
import com.example.demo.Entities.TheLoai;
import com.example.demo.Entities.ThuongHieu;
import com.example.demo.Repositories.ThuongHieuRepository;
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
public class ThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuRepository.findAll();
    }

    public Page<ThuongHieu> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12, Sort.by("ngayTao").descending());
        return thuongHieuRepository.findAll(pageable);
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


    public String GenarateBrandCode() {
        // Lấy mã sản phẩm lớn nhất từ cơ sở dữ liệu
        Integer maxBrandNumber = thuongHieuRepository.getMaxBrandCode();

        // Nếu không có sản phẩm nào, bắt đầu từ SP001
        if (maxBrandNumber == null) {
            return "TH01";
        }

        // Tăng số lên 1 và tạo mã mới theo định dạng SPxxx
        int newBrandNumber = maxBrandNumber + 1;
        return String.format("TH%3d", newBrandNumber);
    }
    // Kiểm tra mã sản phẩm đã tồn tại hay chưa
    public boolean isBrandCodeExist(String ma) {
        return thuongHieuRepository.existsByMa(ma); // Kiểm tra mã sản phẩm có tồn tại trong cơ sở dữ liệu
    }

    public boolean existsByName(String name) {
        return thuongHieuRepository.existsByTen(name); // Gọi repo để kiểm tra
    }

}
