package com.example.demo.Controllers;

import com.example.demo.Entities.KhachHang;
import com.example.demo.Services.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/khachhang")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;
    // lấy tất cả dữ liệu
    @GetMapping
    public List<KhachHang> getAllKhachHang(){
        return khachHangService.getAll();
    }
    // add dữ liệu
    @PostMapping
    public ResponseEntity<KhachHang> addKhachHang(@RequestBody KhachHang khachHang){
        KhachHang newKhachHang = khachHangService.add(khachHang);
        return ResponseEntity.ok(newKhachHang);
    }
    // update khách hàng theo id
    @PutMapping("/{id}")
    public ResponseEntity<KhachHang>updateKhachHang(@PathVariable UUID id, @RequestBody KhachHang khachHangDetail){
        KhachHang updateKhachHang = khachHangService.update(id,khachHangDetail);
        return ResponseEntity.ok(updateKhachHang);
    }
    // delete Khách Hàng theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteKhachHang(@PathVariable UUID id){
        khachHangService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
