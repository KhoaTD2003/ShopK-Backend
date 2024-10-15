package com.example.demo.Controllers;

import com.example.demo.Entities.GiamGia;
import com.example.demo.Services.GiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/giamgia")
public class GiamGiaController {
    @Autowired
    private GiamGiaService service;

    // Lấy tất cả giảm giá
    @GetMapping
    public ResponseEntity<List<GiamGia>> getAllGiamGia() {
        List<GiamGia> giamGias = service.getAll();
        return new ResponseEntity<>(giamGias, HttpStatus.OK);
    }

    // Lấy giảm giá theo ID
    @GetMapping("/{id}")
    public ResponseEntity<GiamGia> getGiamGiaById(@PathVariable UUID id) {
        GiamGia giamGia = service.findById(id);
        if (giamGia != null) {
            return new ResponseEntity<>(giamGia, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Thêm giảm giá mới
    @PostMapping
    public ResponseEntity<GiamGia> addGiamGia(@RequestBody GiamGia giamGia) {
        try {
            GiamGia savedGiamGia = service.add(giamGia);
            return new ResponseEntity<>(savedGiamGia, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // API kiểm tra và giảm số lần sử dụng mã giảm giá
//    @PostMapping("/sudung")
//    public ResponseEntity<String> suDungGiamGia(@RequestParam String maGiamGia) {
//        boolean isSuccess = service.giamSoLanSuDung(maGiamGia);
//        if (isSuccess) {
//            return ResponseEntity.ok("Mã giảm giá đã được áp dụng.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá đã hết số lần sử dụng hoặc không hợp lệ.");
//        }
//    }

    // Cập nhật giảm giá
//    @PutMapping("/{id}")
//    public ResponseEntity<GiamGia> updateGiamGia(@PathVariable UUID id, @RequestBody GiamGia giamGiaDetails) {
//        try {
//            GiamGia updatedGiamGia = service.update(id, giamGiaDetails);
//            return new ResponseEntity<>(updatedGiamGia, HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    // Xóa giảm giá
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiamGia(@PathVariable UUID id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/code")
    public ResponseEntity<?> getDiscountByCode(@RequestParam String maGiamGia) {
        // Lấy mã giảm giá từ database
        Optional<GiamGia> giamGiaOpt = service.getByMa(maGiamGia);

        if (giamGiaOpt.isPresent()) {
            // Trả về thông tin mã giảm giá
            return ResponseEntity.ok(giamGiaOpt.get());
        } else {
            // Nếu mã giảm giá không tồn tại, trả về 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mã giảm giá không tồn tại.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiamGia> updateSoLansd(@PathVariable UUID id, @RequestBody GiamGia giamGiaUpdate) {
        GiamGia updatedGiamGia = service.updateSoLansd(id, giamGiaUpdate.getSoLansd());
        if (updatedGiamGia != null) {
            return ResponseEntity.ok(updatedGiamGia);
        }
        return ResponseEntity.notFound().build();
    }
}
