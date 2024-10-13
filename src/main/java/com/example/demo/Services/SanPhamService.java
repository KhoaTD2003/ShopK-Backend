package com.example.demo.Services;

import com.example.demo.Dtos.SanPhamDto;
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
        Optional<SanPham> optionalSanPham = spRepo.findById(id);
        if (optionalSanPham.isPresent()){
            SanPham sp = optionalSanPham.get();
            sp.setMaSP(sanPham.getMaSP());
            sp.setGiaBan(sanPham.getGiaBan());
            sp.setGiaNhap(sanPham.getGiaNhap());
            sp.setSoLuongTon(sanPham.getSoLuongTon());
            sp.setMoTa(sanPham.getMoTa());
            sp.setAnh(sanPham.getAnh());
            sp.setTrangThai(sanPham.getTrangThai());
            return  spRepo.save(sanPham);
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }

    }


    public void delete(UUID id) {
        Optional<SanPham> optionalSanPham = spRepo.findById(id);
        if (optionalSanPham.isPresent()) {
            spRepo.delete(optionalSanPham.get());
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }
//    public List<SanPhamDto> getAllProductDetails() {
//        return spRepo.findAllProductDetails();
//    }

    // Phương thức lấy danh sách sản phẩm, với tuỳ chọn sắp xếp theo giá
    public List<SanPhamDto> getAllProductDetailsSortedByPrice(String sortOrder) {
        if (sortOrder == null || sortOrder.isEmpty()) {
            // Nếu không có sortOrder, trả về danh sách sản phẩm bình thường (không sắp xếp)
            return spRepo.findAllProductDetails();
        }

        // Kiểm tra sortOrder để sắp xếp theo giá tăng hoặc giảm
        if (sortOrder.equalsIgnoreCase("desc")) {
            return spRepo.findAllProductDetailsSortedByPriceDesc();
        } else {
            return spRepo.findAllProductDetailsSortedByPriceAsc();
        }
    }
}
