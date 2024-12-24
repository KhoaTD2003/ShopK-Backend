package com.example.demo.Services;

import com.example.demo.Entities.MauSac;
import com.example.demo.Entities.TheLoai;
import com.example.demo.Entities.ThuongHieu;
import com.example.demo.Repositories.MauSacRepository;
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
public class MauSacService {
    @Autowired
    private MauSacRepository msRepo;

    public List<MauSac> getAll() {
        return msRepo.findAll();
    }

    public Page<MauSac> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12, Sort.by("ngayTao").descending());
        return msRepo.findAll(pageable);
    }

    public String GenarateColorCode() {
        // Lấy mã sản phẩm lớn nhất từ cơ sở dữ liệu
        Integer maxBrandNumber = msRepo.getMaxColorCode();

        // Nếu không có sản phẩm nào, bắt đầu từ SP001
        if (maxBrandNumber == null) {
            return "MS01";
        }

        // Tăng số lên 1 và tạo mã mới theo định dạng SPxxx
        int newBrandNumber = maxBrandNumber + 1;
        return String.format("MS%3d", newBrandNumber);
    }
    // Kiểm tra mã sản phẩm đã tồn tại hay chưa
    public boolean isColorCodeExist(String ma) {
        return msRepo.existsByMa(ma); // Kiểm tra mã sản phẩm có tồn tại trong cơ sở dữ liệu
    }

    public boolean existsByName(String name) {
        return msRepo.existsByTen(name); // Gọi repo để kiểm tra
    }

    public MauSac add(MauSac mauSac){
        return msRepo.save(mauSac);
    }

    public MauSac update(UUID id, MauSac mauSac){
        Optional<MauSac> optionalMauSac = msRepo.findById(id);
        if(optionalMauSac.isPresent()){
            MauSac ms = optionalMauSac.get();
            ms.setMa(mauSac.getMa());
            ms.setTen(mauSac.getTen());

            return msRepo.save(ms);
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<MauSac> optionalMauSac = msRepo.findById(id);
        if (optionalMauSac.isPresent()) {
            msRepo.delete(optionalMauSac.get());
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }
    public MauSac findById(UUID id) {
        return msRepo.findById(id).orElse(null);
    }
}
