//package com.example.demo.Controllers;
//
//import com.example.demo.Services.SanPhamService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/sanpham")
//public class SanPhamController {
//
//    @Autowired
//    private ChiTietSanPhamService ctspService;
//
//
//    @GetMapping("/hien-thi")
//    public List<ChiTietSanPham> getAll() {
//        return ctspService.getAll();
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<ChiTietSanPham> add(@RequestBody ChiTietSanPham chiTietSanPham) {
//        ChiTietSanPham ctsp = ctspService.add(chiTietSanPham);
//        return ResponseEntity.ok(ctsp);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<ChiTietSanPham> update(@PathVariable UUID id, @RequestBody ChiTietSanPham chiTietSanPham) {
//        ChiTietSanPham ctsp = ctspService.update(id, chiTietSanPham);
//        return ResponseEntity.ok(ctsp);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> delete(@PathVariable UUID id) {
//        ctspService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}
