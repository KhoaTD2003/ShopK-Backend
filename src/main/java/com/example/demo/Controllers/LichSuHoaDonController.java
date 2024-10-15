package com.example.demo.Controllers;

import com.example.demo.Entities.LichSuHoaDon;
import com.example.demo.Entities.PhuongThucTt;
import com.example.demo.Services.LichSuHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/lich-su-hoa-don")
public class LichSuHoaDonController {
    @Autowired
    private LichSuHoaDonService lichSuHoaDonService;

    // Lấy tất cả lịch sử hóa đơn
    @GetMapping
    public ResponseEntity<List<LichSuHoaDon>> getAllLichSuHoaDon() {
        List<LichSuHoaDon> lichSuHoaDons = lichSuHoaDonService.getAll();
        if (lichSuHoaDons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Không có dữ liệu
        }
        return new ResponseEntity<>(lichSuHoaDons, HttpStatus.OK);
    }

    // Thêm lịch sử hóa đơn
    @PostMapping
    public ResponseEntity<LichSuHoaDon> addLichSuHoaDon(@RequestBody LichSuHoaDon lichSuHoaDon) {
        try {
            LichSuHoaDon newLichSuHoaDon = lichSuHoaDonService.add(lichSuHoaDon);
            return new ResponseEntity<>(newLichSuHoaDon, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Dữ liệu không hợp lệ
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Lỗi không xác định
        }
    }

    // Lấy lịch sử hóa đơn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<LichSuHoaDon> getLichSuHoaDonById(@PathVariable UUID id) {
        try {
            LichSuHoaDon lichSuHoaDon = lichSuHoaDonService.getById(id);
            return new ResponseEntity<>(lichSuHoaDon, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Không tìm thấy hóa đơn
        }
    }

    // Cập nhật lịch sử hóa đơn
    @PutMapping("/{id}")
    public ResponseEntity<LichSuHoaDon> updateLichSuHoaDon(@PathVariable UUID id, @RequestBody LichSuHoaDon lichSuHoaDonDetails) {
        try {
            LichSuHoaDon updatedLichSuHoaDon = lichSuHoaDonService.update(id, lichSuHoaDonDetails);
            return new ResponseEntity<>(updatedLichSuHoaDon, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Không tìm thấy hóa đơn để cập nhật
        }
    }

    // Xóa lịch sử hóa đơn
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLichSuHoaDon(@PathVariable UUID id) {
        try {
            lichSuHoaDonService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Không tìm thấy hóa đơn để xóa
        }
    }
    @GetMapping("/paged")
    public Page<LichSuHoaDon> getAllLichSuHoaDonPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size); // Mặc định là 10 phần tử
        return lichSuHoaDonService.getAll(pageable);
    }

}
