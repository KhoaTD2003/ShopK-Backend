package com.example.demo.Controllers;

import com.example.demo.Entities.TheLoai;
import com.example.demo.Entities.XuatXu;
import com.example.demo.Services.TheLoaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/theloai")
public class TheLoaiController {

    @Autowired
    private TheLoaiService theLoaiService;

    @GetMapping
    public List<TheLoai> getAll() {
        return this.theLoaiService.getAll();
    }

    @GetMapping("/page")
    public Page<TheLoai> getAllThuongHieu(@RequestParam(defaultValue = "0") int pageNumber) {
        return theLoaiService.getAll(pageNumber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheLoai> getThuongHieuById(@PathVariable UUID id){
        TheLoai xuatXu = theLoaiService.findById(id);
        return ResponseEntity.ok(xuatXu);
    }


    @PostMapping
    public ResponseEntity<?> add(@RequestBody TheLoai xuatXu) {
        // Kiểm tra mã sản phẩm có tồn tại không
        if (theLoaiService.isCategoryCodeExist(xuatXu.getMa())) {
            return ResponseEntity.badRequest().body("Mã sản phẩm đã tồn tại");
        }
        if (theLoaiService.existsByName(xuatXu.getTen())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Tên thương hiệu đã tồn tại!"));
        }
        // Nếu mã thương hiệu chưa có, tự động sinh mã mới
        if (xuatXu.getMa() == null || xuatXu.getMa().isEmpty()) {
            String newBrandCode = theLoaiService.GenarateCategoryCode(); // Hàm tạo mã mới
            xuatXu.setMa(newBrandCode); // Gán mã mới vào thương hiệu
        }

        // Lưu thương hiệu vào cơ sở dữ liệu
        TheLoai createdXuatXu= theLoaiService.add(xuatXu);

        return new ResponseEntity<>(createdXuatXu, HttpStatus.CREATED);
    }

//    @PostMapping
//    public ResponseEntity<TheLoai> add(@RequestBody TheLoai theLoai) {
//        TheLoai newTheLoai = this.theLoaiService.add(theLoai);
//        return ResponseEntity.ok(newTheLoai);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<TheLoai> update(@PathVariable UUID id, @RequestBody TheLoai theLoaiDetail) {
        TheLoai hang = this.theLoaiService.update(id, theLoaiDetail);
        return ResponseEntity.ok(hang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.theLoaiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
