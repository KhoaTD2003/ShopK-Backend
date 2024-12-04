package com.example.demo.Controllers;

import com.example.demo.Dtos.ChiTietSanPham;
import com.example.demo.Dtos.DatDonRequest;
import com.example.demo.Dtos.NguoiDungDto;
import com.example.demo.Entities.*;
import com.example.demo.Repositories.HoaDonChiTietRepository;
import com.example.demo.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private ChiTietHoaDonService chiTietHoaDonService;

    @Autowired
    private TaiKhoanService service;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private GiamGiaService giamGiaService;

//    @PostMapping("/datdon")
//    public ResponseEntity<String> datDon(@RequestBody DatDonRequest request) {
//        try {
//            // Bước 1: Lưu hoặc cập nhật thông tin người dùng
//            NguoiDungDto nguoiDungDto = request.getNguoiDung();
//            UUID idTaiKhoan = nguoiDungDto.getIdTaiKhoan();
//
//            // Lưu hoặc cập nhật người dùng
//            nguoiDungService.saveOrUpdateNguoiDung(nguoiDungDto);
//
//            // Bước 2: Tạo và lưu hóa đơn mới
//            HoaDon hoaDon = new HoaDon();
//            hoaDon.setMaHoaDon(generateMaHoaDon());
//            hoaDon.setTenKH(nguoiDungDto.getHoTen());
//            hoaDon.setTongTien(request.getTongTien());
//            hoaDon.setTienThu(request.getTienThu());
//            hoaDon.setTienGiam(request.getTienGiam());
//            hoaDon.setGhiChu(request.getGhiChu());
//            hoaDon.setNgayTao(new Date());
//            hoaDon.setTrangThai("Chưa thanh toán");
//
//            // Gán ID tài khoản vào hóa đơn
//            TaiKhoan taiKhoan = new TaiKhoan(idTaiKhoan); // Tạo đối tượng TaiKhoan với ID
//            hoaDon.setTaiKhoan(taiKhoan);
//
//            hoaDonService.add(hoaDon);
//
//            // Bước 3: Lưu thông tin chi tiết hóa đơn
//            for (ChiTietSanPham sanPham : request.getSanPhamList()) {
//                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
//                chiTietHoaDon.setIdHoaDon(hoaDon.getId()); // Gán ID hóa đơn
//                chiTietHoaDon.setIdSanPham(sanPham.getIdSanPham()); // Gán ID sản phẩm
//                chiTietHoaDon.setSoLuong(sanPham.getSoLuong()); // Gán số lượng
//                chiTietHoaDon.setDonGia(sanPham.getDonGia()); // Gán đơn giá
//                chiTietHoaDon.setTongTien(sanPham.getSoLuong() * sanPham.getDonGia()); // Tính tổng tiền
//
//                // Lưu chi tiết hóa đơn
//                chiTietHoaDonService.add(chiTietHoaDon); // Giả sử bạn có một service để lưu chi tiết hóa đơn
//            }
//
//            if (request.getMaGiamGia() != null) {
//                if (giamGiaService.getByMa(request.getMaGiamGia()).isEmpty()) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá không hợp lệ");
//                } else if (giamGiaService.getByMa(request.getMaGiamGia()).get().getSoLansd() == 0) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá đã hết lượt sử dụng");
//                }
//                giamGiaService.giamSoLanSuDung(request.getMaGiamGia());
//            }
//
//            return ResponseEntity.ok("Đặt đơn thành công");
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đặt đơn thất bại");
//        }
//    }

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
            hoaDon.setSdt(request.getNguoiDung().getSdt());
            hoaDon.setTongTien(request.getTongTien());
            hoaDon.setTienThu(request.getTienThu());
            hoaDon.setTienGiam(request.getTienGiam());
            hoaDon.setGhiChu(request.getGhiChu());
//          hoaDon.setNgayTao(new Date());
            hoaDon.setNgayTao(LocalDateTime.now());
            hoaDon.setTrangThai("Chưa thanh toán");
//          hoaDon.setTrangThai(HoaDonStatus.PENDING); // Gán enum thay vì chuỗi "Chưa thanh toán"

            // Gán ID tài khoản vào hóa đơn
            TaiKhoan taiKhoan = new TaiKhoan(idTaiKhoan); // Tạo đối tượng TaiKhoan với ID
            hoaDon.setTaiKhoan(taiKhoan);

            hoaDonService.add(hoaDon);

            // Bước 3: Lưu thông tin chi tiết hóa đơn
            for (ChiTietSanPham sanPham : request.getSanPhamList()) {
                try {
                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                    chiTietHoaDon.setHoaDon(hoaDon);
                    SanPham sanPhamDb = sanPhamService.getProductById(sanPham.getIdSanPham());

                    if (sanPhamDb == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sản phẩm không tồn tại");
                    }

                    chiTietHoaDon.setSanPham(sanPhamDb);
                    chiTietHoaDon.setSoLuong(sanPham.getSoLuong());
                    chiTietHoaDon.setDonGia(BigDecimal.valueOf(sanPham.getDonGia()));
                    chiTietHoaDon.setGhiChu(request.getGhiChu());
                    chiTietHoaDon.setTrangThai(false);
                    chiTietHoaDon.setTongTien(BigDecimal.valueOf(sanPham.getSoLuong() * sanPham.getDonGia()));

                    chiTietHoaDonService.add(chiTietHoaDon);
                } catch (Exception e) {
                    e.printStackTrace(); // In ra lỗi chi tiết
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lưu chi tiết hóa đơn");
                }
                // Lưu chi tiết hóa đơn
//                chiTietHoaDonService.add(chiTietHoaDon); // Giả sử bạn có một service để lưu chi tiết hóa đơn
            }

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

    @PostMapping("/datdon/taiquay")
    public ResponseEntity<String> datDonTT(@RequestBody DatDonRequest request) {
        try {
            // Bước 1: Lưu hoặc cập nhật thông tin người dùng
//            NguoiDungDto nguoiDungDto = request.getNguoiDung();
//            UUID idTaiKhoan = nguoiDungDto.getIdTaiKhoan();

            // Lưu hoặc cập nhật người dùng
//            nguoiDungService.saveOrUpdateNguoiDung(nguoiDungDto);

            // Bước 2: Tạo và lưu hóa đơn mới
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHoaDon(generateMaHoaDon());  // Hàm tự sinh mã hóa đơn
            hoaDon.setTenKH(request.getNguoiDung().getHoTen());
            hoaDon.setSdt(request.getNguoiDung().getSdt());
            hoaDon.setTongTien(request.getTongTien());
            hoaDon.setTienThu(request.getTienThu());
            hoaDon.setTienGiam(request.getTienGiam());
            hoaDon.setGhiChu(request.getGhiChu());
            hoaDon.setNgayTao(LocalDateTime.now());
            hoaDon.setTrangThai("Đã thanh toán");

            // Gán ID tài kh    oản vào hóa đơn
//            TaiKhoan taiKhoan = new TaiKhoan(idTaiKhoan);
//            hoaDon.setTaiKhoan(taiKhoan);

            hoaDonService.add(hoaDon);

            // Bước 3: Lưu thông tin chi tiết hóa đơn
            for (ChiTietSanPham sanPham : request.getSanPhamList()) {
                try {
                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                    chiTietHoaDon.setHoaDon(hoaDon);
                    SanPham sanPhamDb = sanPhamService.getProductById(sanPham.getIdSanPham());

                    if (sanPhamDb == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sản phẩm không tồn tại");
                    }

                    chiTietHoaDon.setSanPham(sanPhamDb);
                    chiTietHoaDon.setSoLuong(sanPham.getSoLuong());
                    chiTietHoaDon.setDonGia(BigDecimal.valueOf(sanPham.getDonGia()));
                    chiTietHoaDon.setGhiChu(request.getGhiChu());
                    chiTietHoaDon.setTrangThai(true); // Trạng thái chi tiết hóa đơn là "Đã thanh toán"
                    chiTietHoaDon.setTongTien(BigDecimal.valueOf(sanPham.getSoLuong() * sanPham.getDonGia()));

                    chiTietHoaDonService.add(chiTietHoaDon);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lưu chi tiết hóa đơn");
                }
            }
            // Kiểm tra mã giảm giá
            if (request.getMaGiamGia() != null) {
                if (giamGiaService.getByMa(request.getMaGiamGia()).isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá không hợp lệ");
                } else if (giamGiaService.getByMa(request.getMaGiamGia()).get().getSoLansd() == 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá đã hết lượt sử dụng");
                }
                giamGiaService.giamSoLanSuDung(request.getMaGiamGia());
            }

            return ResponseEntity.ok("Đặt đơn thành công và hóa đơn đã được thanh toán");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đặt đơn thất bại");
        }
    }

}


