package com.example.demo.Controllers;

import com.example.demo.Entities.GiamGia;
import com.example.demo.Entities.Size;
import com.example.demo.Services.GiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/giamgia")
public class GiamGiaController {
    @Autowired
    private GiamGiaService service;

    // Lấy tất cả giảm giá
    @GetMapping
    public ResponseEntity<List<GiamGia>> getAll() {
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
    @PutMapping("/discount/{id}")
    public ResponseEntity<GiamGia> update(@PathVariable UUID id, @RequestBody GiamGia giamGia) {
        GiamGia gg = service.update(id, giamGia);
        return ResponseEntity.ok(gg);
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

    @GetMapping("/page")
    public Page<GiamGia> getAllGiamGia(@RequestParam(defaultValue = "0") int pageNumber) {
        return service.getAll(pageNumber);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody GiamGia giamGia) {
        // Kiểm tra mã sản phẩm có tồn tại không
        if (service.isSizeCodeExist(giamGia.getMa())) {
            return ResponseEntity.badRequest().body("Mã đã tồn tại");
        }
        if (service.existsByName(giamGia.getTen())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Tên giảm giá đã tồn tại!"));
        }
        // Nếu mã thương hiệu chưa có, tự động sinh mã mới
//        if (xuatXu.getMa() == null || xuatXu.getMa().isEmpty()) {
//            String newBrandCode = sService.GenarateSizeCode(); // Hàm tạo mã mới
//            xuatXu.setMa(newBrandCode); // Gán mã mới vào thương hiệu
//        }

        // Lưu thương hiệu vào cơ sở dữ liệu
        GiamGia createdGiamGia= service.add(giamGia);

        return new ResponseEntity<>(createdGiamGia, HttpStatus.CREATED);
    }



    @PutMapping("/upStatusDiscount/{id}")
    public ResponseEntity<?> updateStatusDiscount(@PathVariable("id") UUID id, @RequestParam("trangThai") boolean trangThai) {
        System.out.println("Product ID: " + id);
        System.out.println("Trang Thai: " + trangThai);

        boolean result = service.updateStatusDiscount(id, trangThai);
        if (result) {
            return ResponseEntity.ok("Trạng thái giảm giá đã được cập nhật thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy giảm giá với ID: " + id);
        }
    }

}
