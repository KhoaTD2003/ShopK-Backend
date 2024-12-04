package com.example.demo.Controllers;

import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Entities.TheLoai;
import com.example.demo.Entities.ThuongHieu;
import com.example.demo.Services.TheLoaiService;
import com.example.demo.Services.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/thuonghieu")
public class ThuongHieuController {

    @Autowired
    private ThuongHieuService service;

    @GetMapping
    public List<ThuongHieu> getAll() {
        return this.service.getAllThuongHieu();
    }


    @GetMapping("/page")
    public Page<ThuongHieu> getAllThuongHieu(@RequestParam(defaultValue = "0") int pageNumber) {
        return service.getAll(pageNumber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThuongHieu> getThuongHieuById(@PathVariable UUID id){
        ThuongHieu thuongHieu = service.getThuongHieuById(id);
        return ResponseEntity.ok(thuongHieu);
    }


    @PostMapping
    public ResponseEntity<?> add(@RequestBody ThuongHieu thuongHieu) {
        // Kiểm tra mã sản phẩm có tồn tại không
        if (service.isBrandCodeExist(thuongHieu.getMa())) {
            return ResponseEntity.badRequest().body("Mã sản phẩm đã tồn tại");
        }
        if (service.existsByName(thuongHieu.getTen())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Tên thương hiệu đã tồn tại!"));
        }
        // Nếu mã thương hiệu chưa có, tự động sinh mã mới
        if (thuongHieu.getMa() == null || thuongHieu.getMa().isEmpty()) {
            String newBrandCode = service.GenarateBrandCode(); // Hàm tạo mã mới
            thuongHieu.setMa(newBrandCode); // Gán mã mới vào thương hiệu
        }

        // Lưu thương hiệu vào cơ sở dữ liệu
        ThuongHieu createdThuongHieu = service.add(thuongHieu);

        return new ResponseEntity<>(createdThuongHieu, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThuongHieu> update(@PathVariable UUID id, @RequestBody ThuongHieu thuongHieued) {
        ThuongHieu updated = this.service.update(id, thuongHieued);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ThuongHieu> delete(@PathVariable UUID id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
