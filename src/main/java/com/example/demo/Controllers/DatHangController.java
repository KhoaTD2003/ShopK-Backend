package com.example.demo.Controllers;

import com.example.demo.Dtos.DatDonRequest;
import com.example.demo.Dtos.NguoiDungDto;
import com.example.demo.Entities.GiamGia;
import com.example.demo.Entities.HoaDon;
import com.example.demo.Entities.NguoiDung;
import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Services.GiamGiaService;
import com.example.demo.Services.HoaDonService;
import com.example.demo.Services.NguoiDungService;
import com.example.demo.Services.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DatHangController {
    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private TaiKhoanService service;

    @Autowired
    private GiamGiaService giamGiaService;

    @PostMapping("/datdon")
    public ResponseEntity<String> datDon(@RequestBody DatDonRequest request) {
        try {
            // Bước 1: Lưu hoặc cập nhật thông tin người dùng
            NguoiDungDto nguoiDungDto = request.getNguoiDung();
            UUID idTaiKhoan = nguoiDungDto.getIdTaiKhoan();

            // Lưu hoặc cập nhật người dùng
            nguoiDungService.saveOrUpdateNguoiDung(nguoiDungDto);

            // Bước 2: Tạo và lưu hóa đơn mới
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHoaDon(generateMaHoaDon());
            hoaDon.setTenKH(nguoiDungDto.getHoTen());
            hoaDon.setTongTien(request.getTongTien());
            hoaDon.setTienThu(request.getTienThu());
            hoaDon.setTienGiam(request.getTienGiam());
            hoaDon.setGhiChu(request.getGhiChu());
            hoaDon.setNgayTao(new Date());
            hoaDon.setTrangThai("Chưa thanh toán");

            // Gán ID tài khoản vào hóa đơn
            TaiKhoan taiKhoan = new TaiKhoan(idTaiKhoan); // Tạo đối tượng TaiKhoan với ID
            hoaDon.setTaiKhoan(taiKhoan);

            hoaDonService.add(hoaDon);

            if (request.getMaGiamGia() != null) {
                if (giamGiaService.getByMa(request.getMaGiamGia()).isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá không hợp lệ");
                } else if (giamGiaService.getByMa(request.getMaGiamGia()).get().getSoLansd() == 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá đã hết lượt sử dụng");
                }
                giamGiaService.giamSoLanSuDung(request.getMaGiamGia());
            }

            return ResponseEntity.ok("Đặt đơn thành công");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đặt đơn thất bại");
        }
    }

    // Phương thức tạo mã hóa đơn
    private String generateMaHoaDon() {
        // Tạo mã hóa đơn ngẫu nhiên, bạn có thể thay đổi cách thức tạo mã
        return "HD" + System.currentTimeMillis(); // Ví dụ: sử dụng thời gian hiện tại
    }
}


