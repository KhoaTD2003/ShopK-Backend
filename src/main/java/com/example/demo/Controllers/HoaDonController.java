package com.example.demo.Controllers;

import com.example.demo.Dtos.ChiTietHoaDonDto;
import com.example.demo.Entities.ChiTietHoaDon;
import com.example.demo.Entities.HoaDon;
import com.example.demo.Repositories.HoaDonChiTietRepository;
import com.example.demo.Services.ChiTietHoaDonService;
import com.example.demo.Services.HoaDonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/api/hoadon")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;


    @Autowired
    private ChiTietHoaDonService chiTietHoaDonService;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    // lấy tất cả dữ liệu
    @GetMapping
    public List<HoaDon> getALlHoaDon() {
        return hoaDonService.getAll();
    }
    // add dữ liệu
    @PostMapping
    public ResponseEntity<HoaDon> addhoaDon(@RequestBody HoaDon hoaDon){
        System.out.println(hoaDon.toString());
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

    @GetMapping("/billdetail/{id}")
    public ResponseEntity<List<ChiTietHoaDonDto>> getChiTietHoaDon(@PathVariable("id") String id) {
        List<ChiTietHoaDonDto> chiTietList = chiTietHoaDonService.getChiTietHoaDonById(UUID.fromString(id));
        if (chiTietList.isEmpty()) {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy
        }
        return ResponseEntity.ok(chiTietList); // Trả về danh sách chi tiết hóa đơn
    }


//    @PutMapping("/{id}/update-details-status")
//    public ResponseEntity<List<ChiTietHoaDon>> updateAllChiTietStatus(
//            @PathVariable UUID id,
//            @RequestParam boolean newStatus) {
//
//        try {
//            List<ChiTietHoaDon> updatedChiTietHoaDons = hoaDonService.updateChiTietHoaDonStatus(id, newStatus);
//            return ResponseEntity.ok(updatedChiTietHoaDons);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//    @PutMapping("/{chiTietId}/update-details-status")
//    public ResponseEntity<ChiTietHoaDon> updateDetailsStatus(
//            @PathVariable UUID chiTietId,
//            @RequestBody Map<String, String> payload) {
//        String newStatus = payload.get("newStatus"); // Lấy trạng thái mới từ request
//        ChiTietHoaDon updatedChiTiet = hoaDonService.updateChiTietHoaDonStatus(chiTietId, HoaDonStatus.valueOf(newStatus));
//        return ResponseEntity.ok(updatedChiTiet);
//    }

    @PutMapping("/{hoaDonId}/update-details-status")
    public ResponseEntity<List<ChiTietHoaDon>> updateChiTietHoaDonStatus(
            @PathVariable UUID hoaDonId,
            @RequestParam String newStatus) {
        try {
            // Chuyển đổi từ String sang boolean
            boolean status = Boolean.parseBoolean(newStatus);

            // Gọi service để cập nhật
            List<ChiTietHoaDon> updatedChiTietHoaDons = hoaDonService.updateChiTietHoaDonStatus(hoaDonId, status);
            return ResponseEntity.ok(updatedChiTietHoaDons);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


//    @GetMapping("/hoa-don/cancelled")
//    public List<HoaDon> getCancelledHoaDons() {
//        return hoaDonService.getCancelledHoaDons();  // Phương thức lấy hóa đơn đã hủy
//    }
}
