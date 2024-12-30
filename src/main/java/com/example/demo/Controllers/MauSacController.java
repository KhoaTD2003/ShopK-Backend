package com.example.demo.Controllers;

import com.example.demo.Entities.MauSac;
import com.example.demo.Entities.Size;
import com.example.demo.Entities.ThuongHieu;
import com.example.demo.Services.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/mausac")
public class MauSacController {
    @Autowired
    private MauSacService msService;


    @GetMapping()
    public List<MauSac> getAll() {
        return msService.getAll();
    }

//    @GetMapping("/page")
//    public Page<MauSac> getAllThuongHieu(@RequestParam(defaultValue = "0") int pageNumber) {
//        return msService.getAll(pageNumber);
//    }

    @GetMapping("/page")
    public Page<MauSac> getAllThuongHieu(@RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(value = "ten", required = false) String ten) {
        return msService.getAll(pageNumber, ten);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MauSac> getThuongHieuById(@PathVariable UUID id){
        MauSac xuatXu = msService.findById(id);
        return ResponseEntity.ok(xuatXu);
    }


    @PostMapping
    public ResponseEntity<?> add(@RequestBody MauSac xuatXu) {
        // Kiểm tra mã sản phẩm có tồn tại không
        if (msService.isColorCodeExist(xuatXu.getMa())) {
            return ResponseEntity.badRequest().body("Mã màu đã tồn tại");
        }
        if (msService.existsByName(xuatXu.getTen())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Tên thương hiệu đã tồn tại!"));
        }
//         Nếu mã thương hiệu chưa có, tự động sinh mã mới
//        if (xuatXu.getMa() == null || xuatXu.getMa().isEmpty()) {
//            String newBrandCode = msService.GenarateColorCode(); // Hàm tạo mã mới
//            xuatXu.setMa(newBrandCode); // Gán mã mới vào thương hiệu
//        }

        // Lưu thương hiệu vào cơ sở dữ liệu
        MauSac createdXuatXu= msService.add(xuatXu);

        return new ResponseEntity<>(createdXuatXu, HttpStatus.CREATED);
    }

//    @PostMapping()
//    public ResponseEntity<MauSac> add(@RequestBody MauSac mauSac) {
//        MauSac ms = msService.add(mauSac);
//        return ResponseEntity.ok(ms);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<MauSac> update(@PathVariable UUID id, @RequestBody MauSac mauSac) {
        MauSac ms = msService.update(id, mauSac);
        return ResponseEntity.ok(ms);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        msService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
