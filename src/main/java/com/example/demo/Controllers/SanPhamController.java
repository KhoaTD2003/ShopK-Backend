package com.example.demo.Controllers;

import com.example.demo.Dtos.SanPhamDto;
import com.example.demo.Entities.SanPham;
import com.example.demo.Entities.Size;
import com.example.demo.Services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/search-and-sort")
    public List<SanPhamDto> searchAndSortProducts(
            @RequestParam(value = "tenSP", required = false) String tenSP,
            @RequestParam(value = "sortOrder", required = false) String sortOrder) {

        // Nếu không có tên tìm kiếm (tenSP), chỉ thực hiện sắp xếp theo giá
        if (tenSP == null || tenSP.isEmpty()) {
            return spService.getAllProductDetailsSortedByPrice(sortOrder);
        }

        // Nếu có tên tìm kiếm (tenSP), tìm kiếm sản phẩm và sắp xếp theo giá nếu có sortOrder
        return spService.searchAndSortProducts(tenSP, sortOrder);
    }

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

    @GetMapping("/page")
    public Page<SanPham> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size) {
        return spService.getAll(page, size);
    }
}
