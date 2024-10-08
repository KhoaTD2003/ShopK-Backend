package com.example.demo.Controller;

import com.example.demo.Entities.SanPham;
import com.example.demo.Services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sanpham")
public class SanPhamController {

    @Autowired
    private SanPhamService spService;


    @GetMapping("/hien-thi")
    public List<SanPham> getAll() {
        return spService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<SanPham> add(@RequestBody SanPham sanPham) {
        SanPham sp = spService.add(sanPham);
        return ResponseEntity.ok(sp);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SanPham> update(@PathVariable UUID id, @RequestBody SanPham sanPham) {
        SanPham sp = spService.update(id, sanPham);
        return ResponseEntity.ok(sp);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        spService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
