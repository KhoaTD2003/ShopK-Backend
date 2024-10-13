package com.example.demo.Controllers;

import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Services.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
            taiKhoanService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @PostMapping("/register")
        public ResponseEntity<TaiKhoan> register(@RequestBody TaiKhoan taiKhoan) {
            TaiKhoan newTaiKhoan = taiKhoanService.register(taiKhoan);
            return ResponseEntity.ok(newTaiKhoan);
        }

        @PostMapping("/login")
        public ResponseEntity<TaiKhoan> login(@RequestParam String tenTaiKhoan, @RequestParam String matKhau) {
            return taiKhoanService.login(tenTaiKhoan, matKhau)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

    }


