package com.example.demo.Services;

import com.example.demo.Dtos.ChiTietHoaDonDto;
import com.example.demo.Dtos.TopSellingProductDTO;
import com.example.demo.Entities.ChiTietHoaDon;
import com.example.demo.Entities.HoaDon;
import com.example.demo.Repositories.HoaDonChiTietRepository;
import com.example.demo.Repositories.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChiTietHoaDonService {

    @Autowired
    private HoaDonChiTietRepository repository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    public ChiTietHoaDon add(ChiTietHoaDon chiTietHoaDon) {
        return repository.save(chiTietHoaDon);
    }

//    public List<ChiTietHoaDonDto> getChiTietHoaDonById(UUID idHoaDon) {
//        List<ChiTietHoaDon> chiTietHoaDonList = repository.findByHoaDonId(idHoaDon);
//        return chiTietHoaDonList.stream().map(chiTiet -> {
//            ChiTietHoaDonDto dto = new ChiTietHoaDonDto();
//            dto.setId(chiTiet.getHoaDon().getId());
//            dto.setMaHoaDon(chiTiet.getHoaDon().getMaHoaDon()); // Lấy mã hóa đơn
//            dto.setTenSanPham(chiTiet.getSanPham().getTenSP()); // Lấy tên sản phẩm
//            dto.setSoLuong(chiTiet.getSoLuong());
//            dto.setDonGia(chiTiet.getDonGia());
//            dto.setTongTien(chiTiet.getTongTien());
//
//            dto.setGhiChu(chiTiet.getGhiChu()); // Lấy ghi chú
//            dto.setTrangThai(chiTiet.getTrangThai()); // Lấy trạng thái
////            dto.setTrangThai(chiTiet.getTrangThai() != null ? chiTiet.getTrangThai() : false); // Giá trị mặc định là false
//
//            return dto;
//        }).collect(Collectors.toList());
//    }

    public List<ChiTietHoaDonDto> getChiTietHoaDonById(UUID idHoaDon) {
            List<ChiTietHoaDon> chiTietHoaDonList = repository.findByHoaDonId(idHoaDon);

    // Lấy hóa đơn từ repository
    HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).orElseThrow(() -> new RuntimeException("Hoa Don not found"));

    // Lấy giá trị giảm giá từ hóa đơn, chuyển đổi thành BigDecimal nếu có
    final BigDecimal giamGia = hoaDon.getTienGiam() != null ? new BigDecimal(hoaDon.getTienGiam()) : BigDecimal.ZERO;

        return chiTietHoaDonList.stream().map(chiTiet -> {
                ChiTietHoaDonDto dto = new ChiTietHoaDonDto();
                dto.setId(chiTiet.getHoaDon().getId());
                dto.setMaHoaDon(chiTiet.getHoaDon().getMaHoaDon()); // Lấy mã hóa đơn
                dto.setTenSanPham(chiTiet.getSanPham().getTenSP()); // Lấy tên sản phẩm
                dto.setSoLuong(chiTiet.getSoLuong());
//            dto.setDonGia(chiTiet.getDonGia());
//            dto.setTongTien(chiTiet.getTongTien());
                dto.setDonGia(String.valueOf(chiTiet.getDonGia())); // Chuyển đổi đơn giá sang chuỗi
                dto.setTongTien(String.valueOf(chiTiet.getHoaDon().getTongTien())); // Chuyển đổi tổng tiền sang chuỗi
                dto.setGiamGia(String.valueOf(chiTiet.getHoaDon().getTienGiam())); // Chuyển đổi tổng tiền sang chuỗi
                dto.setTongTienSauGiamGia(String.valueOf(chiTiet.getHoaDon().getTienThu())); // Chuyển đổi tổng tiền sang chuỗi

                dto.setGhiChu(chiTiet.getGhiChu()); // Lấy ghi chú
                dto.setTrangThai(chiTiet.getTrangThai()); // Lấy trạng thái

        // Tính toán tổng tiền sau giảm giá
//            BigDecimal tongTienSauGiamGia = chiTiet.getTongTien().subtract(chiTiet.getTongTien().multiply(giamGia));
//            dto.setTongTienSauGiamGia(tongTienSauGiamGia);

            return dto;
        }).collect(Collectors.toList());
    }



    public List<TopSellingProductDTO> getTop10SellingProducts() {
        Pageable pageable = PageRequest.of(0, 20); // Lấy 10 sản phẩm đầu tiên
        return repository.findTopSellingProducts(pageable);
    }
}

//    List<ChiTietHoaDon> chiTietHoaDonList = repository.findByHoaDonId(idHoaDon);
//
//    // Lấy hóa đơn từ repository
//    HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).orElseThrow(() -> new RuntimeException("Hoa Don not found"));
//
//    // Lấy giá trị giảm giá từ hóa đơn, chuyển đổi thành BigDecimal nếu có
//    final BigDecimal giamGia = hoaDon.getTienGiam() != null ? new BigDecimal(hoaDon.getTienGiam()) : BigDecimal.ZERO;
//
//        return chiTietHoaDonList.stream().map(chiTiet -> {
//                ChiTietHoaDonDto dto = new ChiTietHoaDonDto();
//                dto.setId(chiTiet.getHoaDon().getId());
//                dto.setMaHoaDon(chiTiet.getHoaDon().getMaHoaDon()); // Lấy mã hóa đơn
//                dto.setTenSanPham(chiTiet.getSanPham().getTenSP()); // Lấy tên sản phẩm
//                dto.setSoLuong(chiTiet.getSoLuong());
////            dto.setDonGia(chiTiet.getDonGia());
////            dto.setTongTien(chiTiet.getTongTien());
//                dto.setDonGia(String.valueOf(chiTiet.getDonGia())); // Chuyển đổi đơn giá sang chuỗi
//                dto.setTongTien(String.valueOf(chiTiet.getTongTien())); // Chuyển đổi tổng tiền sang chuỗi
//                dto.setGiamGia(String.valueOf(chiTiet.getHoaDon().getTienGiam())); // Chuyển đổi tổng tiền sang chuỗi
//                dto.setTongTienSauGiamGia(String.valueOf(chiTiet.getHoaDon().getTienThu())); // Chuyển đổi tổng tiền sang chuỗi
//
//                dto.setGhiChu(chiTiet.getGhiChu()); // Lấy ghi chú
//                dto.setTrangThai(chiTiet.getTrangThai()); // Lấy trạng thái
