package com.example.demo.Controllers;

import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Entities.TheLoai;
import com.example.demo.Entities.ThuongHieu;
import com.example.demo.Services.TheLoaiService;
import com.example.demo.Services.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping
    public ResponseEntity<ThuongHieu> add(@RequestBody ThuongHieu thuongHieu) {
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
