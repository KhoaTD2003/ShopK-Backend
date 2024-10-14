package com.example.demo.Controllers;

import com.example.demo.Entities.HoaDon;
import com.example.demo.Services.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/api/hoadon")
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;
    // lấy tất cả dữ liệu
    @GetMapping
    public List<HoaDon> getALlHoaDon() {

        return hoaDonService.getAll();

    }
    // add dữ liệu
    @PostMapping
    public ResponseEntity<HoaDon> addhoaDon(@RequestBody HoaDon hoaDon){
        HoaDon newHoaDon = hoaDonService.add(hoaDon);
        return ResponseEntity.ok(newHoaDon);
    }
    // update hóa đơn theo id
    @PutMapping("/{id}")
    public ResponseEntity<HoaDon>updatehoaDon(@PathVariable UUID id , @RequestBody HoaDon hoaDonDetail){
        HoaDon updateHoaDon = hoaDonService.update(id,hoaDonDetail);
        return ResponseEntity.ok(updateHoaDon);
    }
    // delete nhân viên theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoaDon(@PathVariable UUID id){
        hoaDonService.delete(id);
        return ResponseEntity.noContent().build();

    }
    // Phương thức lấy danh sách có phân trang
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllHoaDon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pagingg = PageRequest.of(page, size);
        Page<HoaDon> pageHoaDons = hoaDonService.getAll(pagingg);

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageHoaDons.getContent());
        response.put("currentPage", pageHoaDons.getNumber());
        response.put("totalItems", pageHoaDons.getTotalElements());
        response.put("totalPages", pageHoaDons.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
