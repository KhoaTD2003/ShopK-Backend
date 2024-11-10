package com.example.demo.Controllers;

import com.example.demo.Dtos.TaiKhoanDto;
import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Services.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/taikhoan")
public class TaiKhoanController {

    @Autowired
    private TaiKhoanService taiKhoanService;

    // Lấy tất cả tài khoản
    @GetMapping
    public ResponseEntity<List<TaiKhoan>> getAllTaiKhoan() {
        List<TaiKhoan> list = taiKhoanService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Lấy tài khoản theo ID
    @GetMapping("/{id}")
    public ResponseEntity<TaiKhoan> getTaiKhoanById(@PathVariable UUID id) {
        TaiKhoan taiKhoan = taiKhoanService.findById(id);
        return new ResponseEntity<>(taiKhoan, HttpStatus.OK);
    }

    // Thêm tài khoản mới
    @PostMapping
    public ResponseEntity<TaiKhoan> createTaiKhoan(@RequestBody TaiKhoan taiKhoan) {
        TaiKhoan createdTaiKhoan = taiKhoanService.add(taiKhoan);
        return new ResponseEntity<>(createdTaiKhoan, HttpStatus.CREATED);
    }

    // Cập nhật tài khoản theo ID
    @PutMapping("/{id}")
    public ResponseEntity<TaiKhoan> updateTaiKhoan(
            @PathVariable UUID id,
            @RequestBody TaiKhoan taiKhoanDetails) {
        TaiKhoan updatedTaiKhoan = taiKhoanService.update(id, taiKhoanDetails);
        return new ResponseEntity<>(updatedTaiKhoan, HttpStatus.OK);
    }

    // Xóa tài khoản theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaiKhoan(@PathVariable UUID id) {
        taiKhoanService.deleteTaiKhoan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody TaiKhoanDto taiKhoanDto) {
        // Kiểm tra các trường bắt buộc
        if (taiKhoanDto.getTenTaiKhoan() == null || taiKhoanDto.getTenTaiKhoan().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên tài khoản là bắt buộc.");
        }
        if (taiKhoanDto.getMatKhau() == null || taiKhoanDto.getMatKhau().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu là bắt buộc.");
        }
        if (taiKhoanDto.getEmail() == null || taiKhoanDto.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email là bắt buộc.");
        }
        if (taiKhoanDto.getSdt() == null || taiKhoanDto.getSdt().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số điện thoại là bắt buộc.");
        }
        // Kiểm tra trùng lặp tên tài khoản, email, và số điện thoại
        if (taiKhoanService.isTenTaiKhoanExists(taiKhoanDto.getTenTaiKhoan())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tên tài khoản đã được sử dụng.");
        }
        if (taiKhoanService.isEmailExists(taiKhoanDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã được sử dụng.");
        }
        if (taiKhoanService.isSdtExists(taiKhoanDto.getSdt())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Số điện thoại đã được sử dụng.");
        }
        // Thực hiện đăng ký
        TaiKhoanDto newTaiKhoan = taiKhoanService.register(taiKhoanDto);
        return ResponseEntity.ok(newTaiKhoan);
    }

    @PostMapping("/login")
    public ResponseEntity<TaiKhoan> login(@RequestParam String tenTaiKhoan, @RequestParam String matKhau) {
        try {
            TaiKhoan taiKhoan = taiKhoanService.login(tenTaiKhoan, matKhau);
            return ResponseEntity.ok(taiKhoan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Trả về mã 404 nếu thông tin không chính xác
        }
    }



    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        taiKhoanService.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Email đặt lại mật khẩu đã được gửi.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<TaiKhoan> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        TaiKhoan updatedTaiKhoan = taiKhoanService.resetPassword(token, newPassword);
        return ResponseEntity.ok(updatedTaiKhoan);
    }

    //ADMIN


}


