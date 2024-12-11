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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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


    @PostMapping("/datdon")
    public ResponseEntity<String> datDon(@RequestBody DatDonRequest request) {
        try {
            // Bước 1: Lưu hoặc cập nhật thông tin người dùng
            NguoiDungDto nguoiDungDto = request.getNguoiDung();
            UUID idTaiKhoan = nguoiDungDto.getIdTaiKhoan();

            // Lưu hoặc cập nhật người dùng
            nguoiDungService.saveOrUpdateNguoiDung(nguoiDungDto);

            for (ChiTietSanPham sanPham : request.getSanPhamList()) {
                SanPham sanPhamDb = sanPhamService.getProductById(sanPham.getIdSanPham());

                if (sanPhamDb == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sản phẩm không tồn tại");
                }

                // Kiểm tra số lượng tồn kho
                if (sanPhamDb.getSoLuongTon() < sanPham.getSoLuong()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Sản phẩm " + sanPhamDb.getTenSP() + " không đủ số lượng trong kho, còn " + sanPhamDb.getSoLuongTon() + " sản phẩm");
                }
            }

            // Bước 2: Kiểm tra mã giảm giá (nếu có)
            if (request.getMaGiamGia() != null) {
                String maGiamGia = request.getMaGiamGia();

                Optional<GiamGia> giamGiaOptional = giamGiaService.getByMa(maGiamGia);
                BigDecimal giaTriMin = giamGiaOptional.get().getGiaTriMin();
                BigDecimal tongTien = new BigDecimal(request.getTongTien());
                if (tongTien.compareTo(giaTriMin) < 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tổng giá trị đơn hàng chưa đủ để áp dụng mã giảm giá");
                }

                LocalDate ngayBatDau = giamGiaOptional.get().getNgayBatDau().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate ngayKetThuc = giamGiaOptional.get().getNgayKetThuc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate now = LocalDate.now();

                if (now.isBefore(ngayBatDau)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá chưa bắt đầu");
                } else if (now.isAfter(ngayKetThuc)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá hết hiệu lực");
                }

                if (giamGiaOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá không hợp lệ");
                } else if (giamGiaOptional.get().getSoLansd() == 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá đã hết lượt sử dụng");
                }


            }
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
            hoaDon.setTrangThai("Chưa Thanh Toán");
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
//                    SanPham sanPhamDb = sanPhamService.getProductById(sanPham.getIdSanPham());
//
//                    if (sanPhamDb == null) {
//                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sản phẩm không tồn tại");
//                    }
                    SanPham sanPhamDb = sanPhamService.getProductById(sanPham.getIdSanPham());

                    // Giảm số lượng tồn kho
                    sanPhamDb.setSoLuongTon(sanPhamDb.getSoLuongTon() - sanPham.getSoLuong());
                    sanPhamService.updateProduct(sanPhamDb);

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
            // Bước 1: Kiểm tra danh sách sản phẩm có hợp lệ không
            for (ChiTietSanPham sanPham : request.getSanPhamList()) {
                SanPham sanPhamDb = sanPhamService.getProductById(sanPham.getIdSanPham());

                if (sanPhamDb == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sản phẩm không tồn tại");
                }

                // Kiểm tra số lượng tồn kho
                if (sanPhamDb.getSoLuongTon() < sanPham.getSoLuong()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Sản phẩm " + sanPhamDb.getTenSP() + " không đủ số lượng trong kho, còn " + sanPhamDb.getSoLuongTon() + " sản phẩm");
                }
            }

            // Bước 2: Kiểm tra mã giảm giá (nếu có)
            if (request.getMaGiamGia() != null) {
                String maGiamGia = request.getMaGiamGia();

                Optional<GiamGia> giamGiaOptional = giamGiaService.getByMa(maGiamGia);
                if (giamGiaOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá không hợp lệ");
                } else if (giamGiaOptional.get().getSoLansd() == 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá đã hết lượt sử dụng");
                }

                BigDecimal giaTriMin = giamGiaOptional.get().getGiaTriMin();
                BigDecimal tongTien = new BigDecimal(request.getTongTien());
                if (tongTien.compareTo(giaTriMin) < 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tổng giá trị đơn hàng chưa đủ để áp dụng mã giảm giá");
                }

                LocalDate ngayBatDau = giamGiaOptional.get().getNgayBatDau().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate ngayKetThuc = giamGiaOptional.get().getNgayKetThuc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate now = LocalDate.now();

                if (now.isBefore(ngayBatDau)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá chưa bắt đầu");
                } else if (now.isAfter(ngayKetThuc)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá hết hiệu lực");
                }
            }

            // Bước 3: Tạo và lưu hóa đơn mới
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHoaDon(generateMaHoaDon());
            hoaDon.setTenKH(request.getNguoiDung().getHoTen());
            hoaDon.setSdt(request.getNguoiDung().getSdt());
            hoaDon.setTongTien(request.getTongTien());
            hoaDon.setTienThu(request.getTienThu());
            hoaDon.setTienGiam(request.getTienGiam());
            hoaDon.setGhiChu(request.getGhiChu());
            hoaDon.setNgayTao(LocalDateTime.now());
            hoaDon.setTrangThai("Đã Thanh Toán");
            hoaDonService.add(hoaDon);

            // Bước 4: Lưu thông tin chi tiết hóa đơn và cập nhật số lượng tồn kho
            for (ChiTietSanPham sanPham : request.getSanPhamList()) {
                SanPham sanPhamDb = sanPhamService.getProductById(sanPham.getIdSanPham());

                // Giảm số lượng tồn kho
                sanPhamDb.setSoLuongTon(sanPhamDb.getSoLuongTon() - sanPham.getSoLuong());
                sanPhamService.updateProduct(sanPhamDb);

                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                chiTietHoaDon.setHoaDon(hoaDon);
                chiTietHoaDon.setSanPham(sanPhamDb);
                chiTietHoaDon.setSoLuong(sanPham.getSoLuong());
                chiTietHoaDon.setDonGia(BigDecimal.valueOf(sanPham.getDonGia()));
                chiTietHoaDon.setGhiChu(request.getGhiChu());
                chiTietHoaDon.setTrangThai(true);
                chiTietHoaDon.setTongTien(BigDecimal.valueOf(sanPham.getSoLuong() * sanPham.getDonGia()));

                chiTietHoaDonService.add(chiTietHoaDon);
            }

            // Bước 5: Cập nhật số lần sử dụng mã giảm giá (nếu có)
            if (request.getMaGiamGia() != null) {
                giamGiaService.giamSoLanSuDung(request.getMaGiamGia());
            }

            return ResponseEntity.ok("Đặt đơn thành công và hóa đơn đã được thanh toán");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đặt đơn thất bại");
        }
    }


    @GetMapping("/find")
    public String getTenKH(@RequestParam String sdt) {
        String tenKH = hoaDonService.getLatestTenKHBySdt(sdt);
        if (tenKH != null) {
            return tenKH; // Trả về tên khách hàng
        } else {
            return null; // Thông báo nếu không tìm thấy
        }
    }
//    @GetMapping("/find")
//    public String getTenKH(@RequestParam String sdt) {
//        String tenKH = hoaDonService.getTenKHBySdt(sdt);
//        if (tenKH != null) {
//            return tenKH; // Trả về tên khách hàng
//        } else {
//            return "Không tìm thấy khách hàng với số điện thoại này."; // Thông báo nếu không tìm thấy
//        }
//    }
}


