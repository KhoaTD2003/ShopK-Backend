package com.example.demo.Services;

import com.example.demo.Dtos.SanPhamDto;
import com.example.demo.Entities.SanPham;
import com.example.demo.Repositories.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
public class SanPhamService {
    @Autowired
    private SanPhamRepository spRepo;

    public Page<SanPhamDto> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        return spRepo.findAllProductDetails(pageable);
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

    // Phương thức lấy danh sách sản phẩm, với tuỳ chọn sắp xếp theo giá
    public Page<SanPhamDto> getAllProductDetailsSortedByPrice(String sortOrder,int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);

        if (sortOrder == null || sortOrder.isEmpty()) {
            // Nếu không có sortOrder, trả về danh sách sản phẩm bình thường (không sắp xếp)
            return spRepo.findAllProductDetails(pageable);
        }

        // Kiểm tra sortOrder để sắp xếp theo giá tăng hoặc giảm
        if (sortOrder.equalsIgnoreCase("desc")) {
            return spRepo.findAllProductDetailsSortedByPriceDesc(pageable);
        } else {
            return spRepo.findAllProductDetailsSortedByPriceAsc(pageable);
        }
    }

    //search sp theo tên và sort
    public Page<SanPhamDto> searchAndSortProducts(String tenSP, String sortOrder, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        return spRepo.searchAndSortProductsByName(tenSP, sortOrder,pageable);
    }

    public Page<SanPhamDto> findByThuongHieu(String thuongHieu, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        return spRepo.findByThuongHieu(thuongHieu,pageable);
    }


    public Page<SanPhamDto> findByTheLoai(String theLoai, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        return spRepo.findByTheLoai(theLoai,pageable);
    }

    public SanPhamDto findByMaSP(@Param("maSP") String maSP){
      return spRepo.findByMaSP(maSP);
    }

    // lấy sản phẩm theo màu sắc
    public Page<SanPhamDto> findByMauSac(String mauSac, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        return spRepo.findByMauSac(mauSac, pageable);
    }

    // lấy sản phẩm theo kích cỡ
    public Page<SanPhamDto> findBySize(String size, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        return spRepo.findBySize(size, pageable);
    }

    // lấy sản phẩm theo giá trong một khoảng
    public Page<SanPhamDto> findByPriceBetween(double minPrice, double maxPrice, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        return spRepo.findByPriceBetween(minPrice, maxPrice, pageable);
    }
}
