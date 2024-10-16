package com.example.demo.Controllers;

import com.example.demo.Entities.ChiTietHoaDon;
import com.example.demo.Services.ChiTietHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chitiethoadon")
public class ChiTietHoaDonController {

    @Autowired
    private ChiTietHoaDonService chiTietHoaDonService;

    @GetMapping
    public List<ChiTietHoaDon> getChiTietHoaDon() {
        return this.chiTietHoaDonService.getAll();
    }

    //Phân trang
    @GetMapping("/paged")
    public ResponseEntity<Page<ChiTietHoaDon>> getPagedChiTietHoaDon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ChiTietHoaDon> result = chiTietHoaDonService.getAllPaged(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ChiTietHoaDon> add(@RequestBody ChiTietHoaDon chiTietHoaDon) {
        try {
            ChiTietHoaDon savedHoaDon = chiTietHoaDonService.add(chiTietHoaDon);
            return new ResponseEntity<>(savedHoaDon, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietHoaDon> update(@PathVariable UUID id, @RequestBody ChiTietHoaDon chiTietHoaDonDetails) {
        try {
            ChiTietHoaDon newChiTietHoaDon = this.chiTietHoaDonService.update(id, chiTietHoaDonDetails);
            return new ResponseEntity<>(newChiTietHoaDon, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            chiTietHoaDonService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
