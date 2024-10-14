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
import java.util.Map;
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

    // Cập nhật giảm giá
    @PutMapping("/{id}")
    public ResponseEntity<GiamGia> updateGiamGia(@PathVariable UUID id, @RequestBody GiamGia giamGiaDetails) {
        try {
            GiamGia updatedGiamGia = service.update(id, giamGiaDetails);
            return new ResponseEntity<>(updatedGiamGia, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
    // Lấy danh sách có phân trang
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllGiamGia(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pg = (Pageable) PageRequest.of(page, size);
        Page<GiamGia> pageGiamGias = service.getAll((org.springframework.data.domain.Pageable) pg);

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageGiamGias.getContent());
        response.put("currentPage", pageGiamGias.getNumber());
        response.put("totalItems", pageGiamGias.getTotalElements());
        response.put("totalPages", pageGiamGias.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
