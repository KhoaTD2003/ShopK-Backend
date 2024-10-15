package com.example.demo.Services;

import com.example.demo.Entities.ChatLieu;
import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Repositories.ChatLieuRepository;
import com.example.demo.Repositories.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaiKhoanService {

    @Autowired
    private TaiKhoanRepository repository;

    public List<TaiKhoan> getAll() {
        return repository.findAll();
    }

    public TaiKhoan add(TaiKhoan taiKhoan){
        return repository.save(taiKhoan);
    }

    public TaiKhoan findById(UUID id) {
        Optional<TaiKhoan> optionalTaiKhoan = repository.findById(id);
        if(optionalTaiKhoan.isPresent()) {
            return optionalTaiKhoan.get();
        } else {
            throw new RuntimeException("Không tìm thấy tài khoản với ID: " + id);
        }
    }
    // Cập nhật chất liệu theo id
    public TaiKhoan update(UUID id, TaiKhoan taiKhoanDetails){
        Optional<TaiKhoan> optionalTaiKhoan = repository.findById(id);
        if(optionalTaiKhoan.isPresent()){
            TaiKhoan taiKhoan = optionalTaiKhoan.get();
            taiKhoan.setTenTaiKhoan(taiKhoanDetails.getTenTaiKhoan()); // Ví dụ: cập nhật tên
            taiKhoan.setMatKhau(taiKhoanDetails.getMatKhau());
            taiKhoan.setRoLe(taiKhoanDetails.getRoLe());
            taiKhoan.setTrangThai(taiKhoanDetails.isTrangThai());

            // Cập nhật các thuộc tính khác của ChatLieu nếu có
            return repository.save(taiKhoan);
        } else {
            throw new RuntimeException("Không tìm thấy tài khoản với ID: " + id);
        }
    }

    // Xóa chất liệu theo id
    public void delete(UUID id) {
        Optional<TaiKhoan> optionalTaiKhoan = repository.findById(id);
        if(optionalTaiKhoan.isPresent()){
            repository.delete(optionalTaiKhoan.get());
        } else {
            throw new RuntimeException("Không tìm thấy tài khoản với ID: " + id);
        }
    }
    public TaiKhoan register(TaiKhoan taiKhoan) {
        // Kiểm tra xem tài khoản đã tồn tại chưa
        if (repository.findByTenTaiKhoan(taiKhoan.getTenTaiKhoan()).isPresent()) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }
        return repository.save(taiKhoan);
    }

    public Optional<TaiKhoan> login(String tenTaiKhoan, String matKhau) {
        return repository.findByTenTaiKhoan(tenTaiKhoan)
                .filter(taiKhoan -> taiKhoan.getMatKhau().equals(matKhau))
                .map(taiKhoan -> {
                    taiKhoan.setMatKhau("");
                    return taiKhoan;
                }); // Kiểm tra mật khẩu
    }
}
