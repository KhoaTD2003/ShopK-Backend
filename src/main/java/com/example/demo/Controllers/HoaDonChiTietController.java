//package com.example.demo.Controllers;
//
//import com.example.demo.Dtos.ChiTietHoaDonDto;
//import com.example.demo.Services.ChiTietHoaDonService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/billdetail")
//public class HoaDonChiTietController {
//
//    @Autowired
//    private ChiTietHoaDonService chiTietHoaDonService;
//
//    @GetMapping("/{id}")
//    public ResponseEntity<List<ChiTietHoaDonDto>> getChiTietHoaDon(@PathVariable String id) {
//        List<ChiTietHoaDonDto> chiTietList = chiTietHoaDonService.getChiTietHoaDonById(UUID.fromString(id));
//        if (chiTietList.isEmpty()) {
//            return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy
//        }
//        return ResponseEntity.ok(chiTietList); // Trả về danh sách chi tiết hóa đơn
//    }
//}
