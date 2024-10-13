package com.example.demo.Controllers;

import com.example.demo.Dtos.SanPhamDto;
import com.example.demo.Entities.SanPham;
import com.example.demo.Services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sanpham")
public class SanPhamController {

    @Autowired
    private SanPhamService spService;


    @GetMapping()
    public List<SanPham> getAll() {
        return spService.getAll();
    }

    @PostMapping()
    public ResponseEntity<SanPham> add(@RequestBody SanPham sanPham) {
        SanPham ctsp = spService.add(sanPham);
        return ResponseEntity.ok(ctsp);
    }

//    @GetMapping("/details")
//    public ResponseEntity<List<SanPhamDto>> getAllProductDetails() {
//        List<SanPhamDto> productDetails = spService.getAllProductDetails();
//        return ResponseEntity.ok(productDetails);
//    }

    @GetMapping("/sorted-by-price")
    public ResponseEntity<List<SanPhamDto>> getAllProductDetailsSortedByPrice(
            @RequestParam(value = "sortOrder", required = false) String sortOrder) {

        // Gọi service để lấy danh sách sản phẩm với thứ tự sắp xếp
        List<SanPhamDto> products = spService.getAllProductDetailsSortedByPrice(sortOrder);

        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPham> update(@PathVariable UUID id, @RequestBody SanPham sanPham) {
        SanPham sp = spService.update(id, sanPham);
        return ResponseEntity.ok(sp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        spService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
