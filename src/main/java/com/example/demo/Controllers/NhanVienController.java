package com.example.demo.Controllers;

import com.example.demo.Entities.KhachHang;
import com.example.demo.Entities.NhanVien;
import com.example.demo.Services.KhachHangService;
import com.example.demo.Services.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {
    @Autowired
    private NhanVienService nhanVienService;
    // lấy tất cả dữ liệu
    @GetMapping
    public List<NhanVien> getALlNhanVien(){
        return nhanVienService.getAll();
    }
    // add dữ liệu
    @PostMapping
    public ResponseEntity<NhanVien> addNhanVien(@RequestBody NhanVien nhanVien){
        NhanVien newNhanVien = nhanVienService.add(nhanVien);
        return ResponseEntity.ok(newNhanVien);
    }
    // update Nhân viên theo id
    @PutMapping("/{id}")
    public ResponseEntity<NhanVien>updateNhanVien(@PathVariable UUID id , @RequestBody NhanVien nhanVienDetail){
        NhanVien updateNhanVien = nhanVienService.update(id,nhanVienDetail);
        return ResponseEntity.ok(updateNhanVien);
    }
    // delete nhân viên theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable UUID id){
        nhanVienService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
