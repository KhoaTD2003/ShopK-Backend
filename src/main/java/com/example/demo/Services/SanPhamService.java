package com.example.demo.Services;

import com.example.demo.Dtos.SanPhamAdminDto;
import com.example.demo.Dtos.SanPhamDto;
import com.example.demo.Entities.NguoiDung;
import com.example.demo.Entities.SanPham;
import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Repositories.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


import java.util.List;
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
//    public Page<SanPhamDto> searchAndSortProducts(String tenSP, String sortOrder, int pageNumber) {
//        Pageable pageable = PageRequest.of(pageNumber, 12);
//        return spRepo.searchAndSortProductsByName(tenSP, sortOrder,pageable);
//    }
    public Page<SanPhamDto> searchAndSortProducts(String tenSP, String sortOrder, int pageNumber) {
        // Tạo đối tượng Sort tùy thuộc vào giá trị của sortOrder (asc hoặc desc)
        Sort sort = (sortOrder != null && sortOrder.equalsIgnoreCase("desc"))
                ? Sort.by(Sort.Order.desc("giaBan"))  // Sắp xếp giảm dần theo giá
                : Sort.by(Sort.Order.asc("giaBan"));  // Sắp xếp tăng dần theo giá

        // Tạo Pageable với phân trang và sắp xếp
        Pageable pageable = PageRequest.of(pageNumber, 12, sort);

        // Gọi phương thức tìm kiếm và phân trang từ repository
        return spRepo.searchAndSortProductsByName(tenSP, pageable);
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


    //SẢN PHẨM ADMIN
    //
    //
    public Page<SanPhamAdminDto> getAllProducts(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        return spRepo.findAllProduct(pageable);
    }

    public boolean updateStatusProduct(UUID userId, boolean trangThai) {
        SanPham sanPham = spRepo.findById(userId).orElse(null);
        if (sanPham != null) {
            sanPham.setTrangThai(trangThai);
            spRepo.save(sanPham);
            return true;
        } else {
            System.out.println("Không tìm thấy sp với ID: " + userId);
            return false;
        }
    }

    public SanPham getProductById(UUID productId) {
        return spRepo.findById(productId).orElse(null);  // Tìm sptheo UUID
    }

    public boolean deleteProduct(SanPham sanPham) {
        try {
            spRepo.delete(sanPham);
            return true;
        } catch (Exception e) {
            return false;  // Nếu có lỗi xảy ra trong quá trình xóa
        }
    }

    // Cập nhật sản phẩm
    public SanPham updateProduct(SanPham sanPham) {
        return spRepo.save(sanPham);
    }

    // Lấy mã sản phẩm mới (SPxxx)
    public String getMaxProductCode() {
        // Lấy mã sản phẩm lớn nhất từ cơ sở dữ liệu
        Integer maxProductNumber = spRepo.getMaxProductCode();

        // Nếu không có sản phẩm nào, bắt đầu từ SP001
        if (maxProductNumber == null) {
            return "SP01";
        }

        // Tăng số lên 1 và tạo mã mới theo định dạng SPxxx
        int newProductNumber = maxProductNumber + 1;
        return String.format("SP%3d", newProductNumber);
    }
    // Kiểm tra mã sản phẩm đã tồn tại hay chưa
    public boolean isProductCodeExist(String maSP) {
        return spRepo.existsByMaSP(maSP); // Kiểm tra mã sản phẩm có tồn tại trong cơ sở dữ liệu
    }
}
