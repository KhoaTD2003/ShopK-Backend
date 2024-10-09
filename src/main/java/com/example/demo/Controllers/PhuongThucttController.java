package com.example.demo.Controllers;

import com.example.demo.Entities.PhuongThucTt;
import com.example.demo.Repositories.PhuongThucThanhToanRepository;
import com.example.demo.Services.PhuongThucThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/phuongthucthanhtoan")
public class PhuongThucttController {
    @Autowired
    private PhuongThucThanhToanService phuongThucThanhToanService;

    // lấy tất cả dữ liệu thanh toán
    @GetMapping
    public ResponseEntity<List<PhuongThucTt>> getALLPhuongThuctt() {
        List<PhuongThucTt> phuongThucTts = phuongThucThanhToanService.GetALL();
        return new ResponseEntity<>(phuongThucTts, HttpStatus.OK);
    }

    // lấy phương thức thanh toán id
    @GetMapping("/{id}")
    public ResponseEntity<PhuongThucTt> getPhuongthucthanhtoanById(@PathVariable UUID id) {
        PhuongThucTt phuongThucTt = phuongThucThanhToanService.findByID(id);
        if (phuongThucTt != null) {
            return new ResponseEntity<>(phuongThucTt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // thêm phương thức thanh toán mới
    @PostMapping
    public ResponseEntity<PhuongThucTt> addPhuongThucThanhToan(@RequestBody PhuongThucTt phuongThucTt) {
        try {
            PhuongThucTt savedPhuongThuc = phuongThucThanhToanService.add(phuongThucTt);
            return new ResponseEntity<>(savedPhuongThuc, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // cập nhập phương thức thanh toán
    @PutMapping("/{id}")
    public ResponseEntity<PhuongThucTt> updatephuongThucThanhToan(@PathVariable UUID id, @RequestBody PhuongThucTt phuongThucDetail) {
        try {
            PhuongThucTt updatePhuongThuc = phuongThucThanhToanService.update(id, phuongThucDetail);
            return new ResponseEntity<>(updatePhuongThuc, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // xóa phương thức thanh toán
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhuongThucThanhToan(@PathVariable UUID id) {
        try {
            phuongThucThanhToanService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
