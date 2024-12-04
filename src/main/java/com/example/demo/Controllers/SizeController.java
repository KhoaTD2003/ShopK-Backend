package com.example.demo.Controllers;

import com.example.demo.Entities.Size;
import com.example.demo.Entities.TheLoai;
import com.example.demo.Services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/size")
public class SizeController {
    @Autowired
    private SizeService sService;


    @GetMapping()
    public List<Size> getAll() {
        return sService.getAll();
    }

    @GetMapping("/page")
    public Page<Size> getAllThuongHieu(@RequestParam(defaultValue = "0") int pageNumber) {
        return sService.getAll(pageNumber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Size> getThuongHieuById(@PathVariable UUID id){
        Size xuatXu = sService.findById(id);
        return ResponseEntity.ok(xuatXu);
    }


    @PostMapping
    public ResponseEntity<?> add(@RequestBody Size xuatXu) {
        // Kiểm tra mã sản phẩm có tồn tại không
        if (sService.isSizeCodeExist(xuatXu.getMa())) {
            return ResponseEntity.badRequest().body("Mã sản phẩm đã tồn tại");
        }
        if (sService.existsByName(xuatXu.getTen())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Tên thương hiệu đã tồn tại!"));
        }
        // Nếu mã thương hiệu chưa có, tự động sinh mã mới
        if (xuatXu.getMa() == null || xuatXu.getMa().isEmpty()) {
            String newBrandCode = sService.GenarateSizeCode(); // Hàm tạo mã mới
            xuatXu.setMa(newBrandCode); // Gán mã mới vào thương hiệu
        }

        // Lưu thương hiệu vào cơ sở dữ liệu
        Size createdXuatXu= sService.add(xuatXu);

        return new ResponseEntity<>(createdXuatXu, HttpStatus.CREATED);
    }

//    @PostMapping()
//    public ResponseEntity<Size> add(@RequestBody Size size) {
//        Size s = sService.add(size);
//        return ResponseEntity.ok(s);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Size> update(@PathVariable UUID id, @RequestBody Size size) {
        Size s = sService.update(id, size);
        return ResponseEntity.ok(s);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
