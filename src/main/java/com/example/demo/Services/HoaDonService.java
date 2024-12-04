package com.example.demo.Services;

import com.example.demo.Dtos.ChiTietHoaDonDto;
import com.example.demo.Entities.ChiTietHoaDon;
import com.example.demo.Entities.HoaDon;
import com.example.demo.Repositories.HoaDonChiTietRepository;
import com.example.demo.Repositories.HoaDonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository repository;

    @Autowired
    private HoaDonChiTietRepository chiTietHoaDonRepository;

    public List<HoaDon> getAll() {
//        return repository.findAll();
        return repository.findAll(Sort.by(Sort.Order.desc("ngayTao"))); // Sắp xếp theo ngày tạo, mới nhất lên đầu

    }

    public HoaDon add(HoaDon hoaDon) {
//        hoaDon.setNgayTao(new Date());
        hoaDon.setNgayTao(LocalDateTime.now());
//        hoaDon.setTrangThai("Chưa thanh toán"); // Trạng thái mặc định
        return repository.save(hoaDon);
    }

//    public HoaDon add(HoaDon hoaDon) {
////        hoaDon.setNgayTao(new Date());
//        hoaDon.setNgayTao(LocalDateTime.now());
//
//        hoaDon.setTrangThai(HoaDonStatus.PENDING); // Trạng thái mặc định
//        return repository.save(hoaDon);
//    }

    // update dữ liệu theo id
    public HoaDon update(UUID id, HoaDon hoaDonDetail){
        Optional<HoaDon> optionalHoaDon = repository.findById(id);
        if(optionalHoaDon.isPresent()){
            HoaDon hoaDon = optionalHoaDon.get();
            hoaDon.setMaHoaDon(hoaDonDetail.getMaHoaDon());
            hoaDon.setTenKH(hoaDonDetail.getTenKH());
            hoaDon.setTongTien(hoaDonDetail.getTongTien());
            hoaDon.setTienThu(hoaDonDetail.getTienThu());
            hoaDon.setTienGiam(hoaDonDetail.getTienGiam());
            hoaDon.setNgayTao(LocalDateTime.now());

//            hoaDon.setNgayTao(new Date());
            hoaDon.setTrangThai(hoaDonDetail.getTrangThai());
            hoaDon.setGhiChu(hoaDonDetail.getGhiChu());
            return repository.save(hoaDon);
        }else {
            throw new RuntimeException("không tìm thấy Hóa Đơn ID "+id);
        }
    }
    // delete dữ liệu theo id
    public void delete(UUID id){
        Optional<HoaDon>optionalHoaDon = repository.findById(id);
        if(optionalHoaDon.isPresent()){
            repository.delete(optionalHoaDon.get());
        }else{
            throw new RuntimeException("không tìm thấy hóa đơn ID "+id);
        }
    }




    public List<ChiTietHoaDon> updateChiTietHoaDonStatus(UUID hoaDonId, boolean newStatus) {
        // Lấy tất cả các chi tiết hóa đơn theo ID hóa đơn
        List<ChiTietHoaDon> chiTietHoaDons = chiTietHoaDonRepository.findByHoaDonId(hoaDonId);

        if (chiTietHoaDons.isEmpty()) {
            throw new EntityNotFoundException("Không tìm thấy chi tiết hóa đơn nào cho hóa đơn với ID: " + hoaDonId);
        }

        // Cập nhật trạng thái của từng chi tiết hóa đơn
        for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
            chiTiet.setTrangThai(newStatus);
        }
        chiTietHoaDonRepository.saveAll(chiTietHoaDons);

        // Kiểm tra trạng thái của tất cả chi tiết hóa đơn
        boolean allPaid = chiTietHoaDons.stream()
                .allMatch(ChiTietHoaDon::getTrangThai);

        // Lấy hóa đơn liên quan
        HoaDon hoaDon = repository.findById(hoaDonId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy hóa đơn với ID: " + hoaDonId));

        // Cập nhật trạng thái của hóa đơn
        hoaDon.setTrangThai(allPaid ? "Đã Thanh Toán" : "Chưa Thanh Toán");
        repository.save(hoaDon);

        return chiTietHoaDons;
    }

//    public List<ChiTietHoaDon> updateChiTietHoaDonStatus(UUID hoaDonId, boolean newStatus) {
//        // Lấy tất cả các chi tiết hóa đơn theo ID hóa đơn
//        List<ChiTietHoaDon> chiTietHoaDons = chiTietHoaDonRepository.findByHoaDonId(hoaDonId);
//
//        if (chiTietHoaDons.isEmpty()) {
//            throw new EntityNotFoundException("Không tìm thấy chi tiết hóa đơn nào cho hóa đơn với ID: " + hoaDonId);
//        }
//
//        // Cập nhật trạng thái boolean cho từng chi tiết hóa đơn
//        for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
//            chiTiet.setTrangThai(newStatus); // true: Đã thanh toán, false: Chưa thanh toán
//        }
//        chiTietHoaDonRepository.saveAll(chiTietHoaDons);
//
//        // Kiểm tra nếu tất cả chi tiết hóa đơn đã được thanh toán
//        boolean allPaid = chiTietHoaDons.stream()
//                .allMatch(ChiTietHoaDon::getTrangThai); // Kiểm tra nếu tất cả trạng thái là true
//
//        // Lấy hóa đơn liên quan
//        HoaDon hoaDon = repository.findById(hoaDonId)
//                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy hóa đơn với ID: " + hoaDonId));
//
//        // Cập nhật trạng thái hóa đơn dựa trên trạng thái của các chi tiết
//        if (allPaid) {
//            hoaDon.setTrangThai(HoaDonStatus.PAID); // Tất cả chi tiết đã thanh toán
//        } else {
//            hoaDon.setTrangThai(HoaDonStatus.PENDING); // Còn chi tiết chưa thanh toán
//        }
//
//        // Lưu hóa đơn sau khi cập nhật
//        repository.save(hoaDon);
//
//        return chiTietHoaDons;
//    }


//    @Scheduled(fixedRate = 3600000)  // Chạy mỗi 1 giờ (3600000ms)
//    public void autoCancelHoaDon() {
//        // Lấy tất cả hóa đơn có trạng thái "Chưa thanh toán"
//        Iterable<HoaDon> hoaDons = repository.findByTrangThai(HoaDonStatus.PENDING);
//
//        for (HoaDon hoaDon : hoaDons) {
//            // Kiểm tra xem hóa đơn đã tồn tại quá 24 giờ chưa
//            if (ChronoUnit.HOURS.between(hoaDon.getNgayTao(), LocalDateTime.now()) > 24) {
//                hoaDon.setTrangThai(HoaDonStatus.CANCELLED); // Cập nhật trạng thái thành "Đã hủy"
//                repository.save(hoaDon); // Lưu lại trạng thái mới
//                System.out.println("Hóa đơn " + hoaDon.getId() + " đã được tự động hủy.");
//            }
//        }
//    }

//    public List<HoaDon> getCancelledHoaDons() {
//        return repository.findByTrangThai(HoaDonStatus.CANCELLED);
//    }
}
