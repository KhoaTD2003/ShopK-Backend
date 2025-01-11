package com.example.demo.Services;

import com.example.demo.Dtos.ChiTietHoaDonDto;
import com.example.demo.Entities.ChiTietHoaDon;
import com.example.demo.Entities.HoaDon;
import com.example.demo.Entities.MauSac;
import com.example.demo.Repositories.HoaDonChiTietRepository;
import com.example.demo.Repositories.HoaDonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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

//    public List<HoaDon> getAll() {
////        return repository.findAll();
//        return repository.findAll(Sort.by(Sort.Order.desc("ngayTao"))); // Sắp xếp theo ngày tạo, mới nhất lên đầu
//
//    }

    public Page<HoaDon> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12, Sort.by("ngayTao").descending());
        return repository.findAll(pageable);
    }

//    public Page<HoaDon> getHoaDon(int pageNumber, int pageSize, String maHoaDon, String sdt) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("ngayTao").descending());
//
//        // Kiểm tra nếu có tham số tìm kiếm
//        if (maHoaDon != null && !maHoaDon.isEmpty() || sdt != null && !sdt.isEmpty()) {
//            // Tìm kiếm theo mã hóa đơn hoặc số điện thoại
//            return repository.findByMaHoaDonOrSdtContaining(maHoaDon, sdt, pageable);
//        } else {
//            // Nếu không có tham số tìm kiếm, lấy tất cả hóa đơn
//            return repository.findAll(pageable);
//        }
//    }


    public Page<HoaDon> getHoaDon(int pageNumber, int pageSize, String maHoaDon, String sdt, String trangThai, String ghiChu) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("ngayTao").descending());

        // Kiểm tra nếu có tham số tìm kiếm theo mã hóa đơn hoặc số điện thoại
        if ((maHoaDon != null && !maHoaDon.isEmpty()) || (sdt != null && !sdt.isEmpty())) {
            // Tìm kiếm theo mã hóa đơn hoặc số điện thoại
            return repository.findByMaHoaDonOrSdtContaining(maHoaDon, sdt, pageable);
        } else if ((trangThai != null && !trangThai.isEmpty()) || (ghiChu != null && !ghiChu.isEmpty())) {
            // Tìm kiếm theo trạng thái và ghi chú
            return repository.findByTrangThaiAndGhiChuContaining(trangThai, ghiChu, pageable);
        } else {
            // Nếu không có tham số tìm kiếm, lấy tất cả hóa đơn
            return repository.findAll(pageable);
        }
    }

    public Page<HoaDon> findByTrangThai(String trangThai, String ghiChu, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);  // Tạo Pageable từ số trang và kích thước trang
        return repository.findByTrangThaiAndGhiChuContaining(trangThai, ghiChu, pageable);
    }

    public HoaDon findById(UUID id) {
        Optional<HoaDon> hoaDon = repository.findById(id);
        return hoaDon.orElse(null); // Trả về null nếu không tìm thấy
    }
//    public Page<HoaDon> getHoaDon2(int pageNumber, int pageSize, String maHoaDon, String sdt, String ghiChu, String trangThai) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("ngayTao").descending());
//
//        // Kiểm tra nếu có tham số tìm kiếm
//        if (maHoaDon != null && !maHoaDon.isEmpty() || sdt != null && !sdt.isEmpty() || ghiChu != null || trangThai != null) {
//            // Tìm kiếm theo mã hóa đơn hoặc số điện thoại
//            return repository.findByMaHoaDonOrSdtContainingAndFilters(maHoaDon, sdt,ghiChu,trangThai ,pageable );
//        } else {
//            // Nếu không có tham số tìm kiếm, lấy tất cả hóa đơn
//            return repository.findAll(pageable);
//        }
//    }

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

    public HoaDon updateCancel(UUID id) {
        // Tìm hóa đơn theo id
        Optional<HoaDon> optionalHoaDon = repository.findById(id);

        if (optionalHoaDon.isPresent()) {
            HoaDon hoaDon = optionalHoaDon.get();

            // Cập nhật trạng thái hóa đơn thành "Đã hủy"
            hoaDon.setTrangThai("Cancle");

            // Lưu lại hóa đơn với trạng thái mới
            return repository.save(hoaDon);
        } else {
            // Nếu không tìm thấy hóa đơn với id, ném ngoại lệ với thông báo chi tiết
            throw new RuntimeException("Không tìm thấy hóa đơn với ID: " + id);
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

    //tim kh theo sdt
//    public String getTenKHBySdt(String sdt) {
//        List<HoaDon> hoaDons = repository.findBySdt(sdt);
//        if (!hoaDons.isEmpty()) {
//            return hoaDons.get(0).getTenKH(); // Lấy tên khách hàng từ hóa đơn đầu tiên
//        }
//        return null;
//    }

    public String getLatestTenKHBySdt(String sdt) {
        List<HoaDon> hoaDons = repository.findLatestBySdt(sdt);
        if (!hoaDons.isEmpty()) {
            return hoaDons.get(0).getTenKH(); // Lấy tên khách hàng từ hóa đơn mới nhất
        }
        return null; // Trả về null nếu không tìm thấy
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

    // Lấy tổng số hóa đơn "Chưa Thanh Toán" theo thời gian (ngày hoặc tuần)
    public long countUnpaid(String timePeriod, LocalDateTime start, LocalDateTime end) {
        if ("day".equalsIgnoreCase(timePeriod)) {
            return repository.countUnpaidByDay(start, end);
        } else if ("week".equalsIgnoreCase(timePeriod)) {
            return repository.countUnpaidByWeek(start, end);
        }else if ("month".equalsIgnoreCase(timePeriod)) {
            int month = start.getMonthValue();
            int year = start.getYear();
            return repository.countUnpaidByMonth(month, year);        }
        throw new IllegalArgumentException("Invalid time period. Must be 'day' or 'week'.");
    }

    // Các phương thức khác vẫn giữ nguyên, ví dụ:
    // Lấy tổng số hóa đơn "Đã Thanh Toán" theo thời gian (ngày hoặc tuần)
    public long countPaid(String timePeriod, LocalDateTime start, LocalDateTime end) {
        if ("day".equalsIgnoreCase(timePeriod)) {
            return repository.countPaidByDay(start, end);
        } else if ("week".equalsIgnoreCase(timePeriod)) {
            return repository.countPaidByWeek(start, end);
        }else if ("month".equalsIgnoreCase(timePeriod)) {
            int month = start.getMonthValue();
            int year = start.getYear();
            return repository.countPaidByMonth(month,year);
        }
        throw new IllegalArgumentException("Invalid time period. Must be 'day' or 'week'.");
    }

    // Lấy tổng số hóa đơn "Đã Hủy" theo thời gian (ngày hoặc tuần)
    public long countCancelled(String timePeriod, LocalDateTime start, LocalDateTime end) {
        if ("day".equalsIgnoreCase(timePeriod)) {
            return repository.countCancelledByDay(start, end);
        } else if ("week".equalsIgnoreCase(timePeriod)) {
            return repository.countCancelledByWeek(start, end);
        }else if ("month".equalsIgnoreCase(timePeriod)) {
            return repository.countCancelledByMonth(start);
        }
        throw new IllegalArgumentException("Invalid time period. Must be 'day' or 'week'.");
    }

    // Lấy tổng doanh thu (tổng tiền) cho hóa đơn đã thanh toán
    public Double calculateTotalRevenue() {
        return repository.calculateTotalRevenue();
    }

    // Tính tổng doanh thu theo trạng thái và khoảng thời gian (ngày, tuần, tháng)
    public BigDecimal calculateRevenueByDateRange(String timePeriod) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        // Tính toán ngày bắt đầu và ngày kết thúc theo khoảng thời gian
        switch (timePeriod.toLowerCase()) {
            case "day":
                startDate = LocalDate.now().atStartOfDay(); // Chuyển LocalDate thành LocalDateTime
                endDate = startDate.plusDays(1).minusNanos(1); // Đến hết ngày hôm nay
                break;
            case "week":
                startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay(); // Bắt đầu từ đầu tuần
                endDate = startDate.plusWeeks(1).minusNanos(1); // Đến hết tuần này
                break;
            case "month":
                startDate = LocalDate.now().withDayOfMonth(1).atStartOfDay(); // Bắt đầu từ ngày 1 của tháng
                endDate = startDate.plusMonths(1).minusNanos(1); // Đến hết tháng này
                break;
            default:
                throw new IllegalArgumentException("Invalid time period: " + timePeriod);
        }

        // Gọi repository để tính tổng doanh thu
        return repository.calculateRevenueByStatusAndDateRange(startDate, endDate);
    }
    }