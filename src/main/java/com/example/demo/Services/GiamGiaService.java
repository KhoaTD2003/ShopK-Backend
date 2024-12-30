package com.example.demo.Services;

import com.example.demo.Entities.GiamGia;
import com.example.demo.Entities.SanPham;
import com.example.demo.Entities.Size;
import com.example.demo.Repositories.GiamGiaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class GiamGiaService {
    @Autowired
    private GiamGiaRepository repository;

    // Lấy tất cả giảm giá
    public List<GiamGia> getAll() {
        return repository.findAll();
    }

    // Lấy giảm giá theo ID
    public GiamGia findById(UUID id) {
        Optional<GiamGia> optional = repository.findById(id);
        return optional.orElse(null);
    }

    // Thêm giảm giá mới
    public GiamGia add(GiamGia giamGia) {
        return repository.save(giamGia);
    }

    // Cập nhật giảm giá
    public GiamGia update(UUID id, GiamGia giamGiaDetails) {
        Optional<GiamGia> optional = repository.findById(id);
        if (optional.isPresent()) {
            GiamGia existingGiamGia = optional.get();
            existingGiamGia.setMa(giamGiaDetails.getMa());
            existingGiamGia.setTen(giamGiaDetails.getTen());
            existingGiamGia.setNgayBatDau(giamGiaDetails.getNgayBatDau());
            existingGiamGia.setNgayKetThuc(giamGiaDetails.getNgayKetThuc());
            existingGiamGia.setGiamGia(giamGiaDetails.getGiamGia());
            existingGiamGia.setGiaTriMin(giamGiaDetails.getGiaTriMin());
            existingGiamGia.setSoLansd(giamGiaDetails.getSoLansd());
            existingGiamGia.setTrangThai(giamGiaDetails.getTrangThai());
            return repository.save(existingGiamGia);
        }
        throw new RuntimeException("Không tìm thấy giảm giá");
    }

    // Xóa giảm giá
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    // Tìm mã giảm giá theo mã
    public Optional<GiamGia> getByMa(String maGiamGia) {
        return repository.findByMa(maGiamGia);
    }

    // Cập nhật số lần sử dụng
    public boolean giamSoLanSuDung(String maGiamGia) {
        Optional<GiamGia> giamGiaOptional = repository.findByMa(maGiamGia);
        if (giamGiaOptional.isPresent()) {
            GiamGia giamGia = giamGiaOptional.get();
            if (giamGia.getSoLansd() > 0) {
                giamGia.setSoLansd(giamGia.getSoLansd() - 1); // Giảm số lần sử dụng
                repository.save(giamGia);
                return true;
            }
        }
        return false; // Không thể giảm số lần sử dụng (mã hết hạn hoặc không hợp lệ)
    }


    public GiamGia updateSoLansd(UUID id, int newSoLansd) {
        Optional<GiamGia> giamGiaOpt = repository.findById(id);
        if (giamGiaOpt.isPresent()) {
            GiamGia giamGia = giamGiaOpt.get();
            giamGia.setSoLansd(newSoLansd);
            return repository.save(giamGia); // Lưu lại thay đổi
        }
        return null; // Trả về null nếu không tìm thấy mã giảm giá
    }

    public Page<GiamGia> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12, Sort.by("ngayTao").descending());
        return repository.findAll(pageable);
    }

    public Page<GiamGia> getGiamGia(int pageNumber, String ten, String ma, Boolean trangThai) {
        Pageable pageable = PageRequest.of(pageNumber, 12, Sort.by("ngayTao").descending());

        if (ten != null && !ten.isEmpty()|| ma != null && !ma.isEmpty()) {
            return repository.findByTenContainingOrMaContaining(ten, ma, pageable);
        } else if (trangThai != null) {
            return repository.findByTrangThai(trangThai, pageable);
        }
//        else if (giamGia != null && !giamGia.isEmpty()) {
//            return repository.findByGiamGiaType(giamGia, pageable);
//        }
        else {
            return repository.findAll(pageable); // Trả về tất cả nếu không có điều kiện
        }
    }

    public String GenarateSizeCode() {
        // Lấy mã sản phẩm lớn nhất từ cơ sở dữ liệu
        Integer maxBrandNumber = repository.getMaxDiscountCode();

        // Nếu không có sản phẩm nào, bắt đầu từ SP001
        if (maxBrandNumber == null) {
            return "GG01";
        }

        // Tăng số lên 1 và tạo mã mới theo định dạng SPxxx
        int newBrandNumber = maxBrandNumber + 1;
        return String.format("GG%3d", newBrandNumber);
    }
    // Kiểm tra mã sản phẩm đã tồn tại hay chưa
    public boolean isSizeCodeExist(String ma) {
        return repository.existsByMa(ma); // Kiểm tra mã sản phẩm có tồn tại trong cơ sở dữ liệu
    }

    public boolean existsByName(String name) {
        return repository.existsByTen(name); // Gọi repo để kiểm tra
    }

    public boolean updateStatusDiscount(UUID id, boolean trangThai) {
        GiamGia giamGia = repository.findById(id).orElse(null);
        if (giamGia != null) {
            giamGia.setTrangThai(trangThai);
            repository.save(giamGia);
            return true;
        } else {
            System.out.println("Không tìm thấy sp với ID: " + id);
            return false;
        }
    }

    // Cập nhật trạng thái giảm giá khi hết hạn
    @Transactional
    public void updateExpiredDiscounts() {
        Date today = new Date();
        repository.updateExpiredDiscounts(today);
    }
}
