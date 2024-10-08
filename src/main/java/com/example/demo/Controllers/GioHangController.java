package com.example.demo.Controllers;


import com.example.demo.Entities.GioHang;
import com.example.demo.Services.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

    @RestController
    @RequestMapping("/api/giohang")
    public class GioHangController {

        @Autowired
        private GioHangService gioHangService;

        // Lấy tất cả giỏ hàng
        @GetMapping
        public ResponseEntity<List<GioHang>> getAllGioHang() {
            List<GioHang> gioHangs = gioHangService.getAll();
            return new ResponseEntity<>(gioHangs, HttpStatus.OK);
        }

        // Lấy giỏ hàng theo ID
        @GetMapping("/{id}")
        public ResponseEntity<GioHang> getGioHangById(@PathVariable UUID id) {
            GioHang gioHang = gioHangService.findById(id);
            if (gioHang != null) {
                return new ResponseEntity<>(gioHang, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        // Thêm giỏ hàng mới
        @PostMapping
        public ResponseEntity<GioHang> addGioHang(@RequestBody GioHang gioHang) {
            try {
                GioHang savedGioHang = gioHangService.add(gioHang);
                return new ResponseEntity<>(savedGioHang, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        // Cập nhật giỏ hàng
        @PutMapping("/{id}")
        public ResponseEntity<GioHang> updateGioHang(@PathVariable UUID id, @RequestBody GioHang gioHangDetails) {
            try {
                GioHang updatedGioHang = gioHangService.update(id, gioHangDetails);
                return new ResponseEntity<>(updatedGioHang, HttpStatus.OK);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        // Xóa giỏ hàng
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteGioHang(@PathVariable UUID id) {
            try {
                gioHangService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }


