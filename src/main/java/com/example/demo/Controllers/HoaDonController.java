package com.example.demo.Controllers;

import com.example.demo.Dtos.ChiTietHoaDonDto;
//import com.example.demo.Dtos.HoaDonDto;
//import com.example.demo.Dtos.RevenuePerDayDto;
import com.example.demo.Entities.ChiTietHoaDon;
import com.example.demo.Entities.HoaDon;
import com.example.demo.Entities.Size;
import com.example.demo.Repositories.HoaDonChiTietRepository;
import com.example.demo.Services.ChiTietHoaDonService;
import com.example.demo.Services.HoaDonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
//    @GetMapping
//    public List<HoaDon> getALlHoaDon() {
//        return hoaDonService.getAll();
//    }

//    @GetMapping()
//    public Page<HoaDon> getALlHoaDon(@RequestParam(defaultValue = "0") int pageNumber,
//                                     @RequestParam(defaultValue = "12") int pageSize,  // Kích thước trang, mặc định là 12
//                                     @RequestParam(value = "maHoaDon",required = false) String maHoaDon,
//                                     @RequestParam(value = "sdt",required = false) String sdt )
//
//    {
//        return hoaDonService.getHoaDon(pageNumber, pageSize, maHoaDon, sdt);
//    }

//    @GetMapping()
//    public Page<HoaDon> getHoaDons(
//            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,  // Mặc định trang 0
//            @RequestParam(value = "size", defaultValue = "12") int size,  // Mặc định kích thước trang là 12
//            @RequestParam(value = "maHoaDon", required = false) String maHoaDon,  // Mã hóa đơn, có thể null
//            @RequestParam(value = "sdt", required = false) String sdt,  // Số điện thoại, có thể null
//            @RequestParam(value = "trangThai", required = false) String trangThai,  // Trạng thái hóa đơn, có thể null
//            @RequestParam(value = "ghiChu", required = false) String ghiChu) {  // Ghi chú, có thể null
//
//        // Kiểm tra các tham số và gọi phương thức tương ứng trong service
//        if ((maHoaDon != null && !maHoaDon.isEmpty()) || (sdt != null && !sdt.isEmpty())) {
//            // Nếu tìm kiếm theo maHoaDon hoặc sdt
//            return hoaDonService.getHoaDon(pageNumber, size, maHoaDon, sdt);
//        } else if ((trangThai != null && !trangThai.isEmpty()) || (ghiChu != null && !ghiChu.isEmpty())) {
//            // Nếu tìm kiếm theo trangThai hoặc ghiChu
//            return hoaDonService.findByTrangThai(trangThai, ghiChu, pageNumber, size);
//        } else {
//            // Nếu không có tham số tìm kiếm, lấy tất cả hóa đơn
//            return hoaDonService.getHoaDon(pageNumber, size, null, null);  // Gọi phương thức lấy tất cả hóa đơn
//        }
//    }

    @GetMapping()
    public Page<HoaDon> getHoaDon(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "12") int pageSize,
            @RequestParam(value = "maHoaDon", required = false) String maHoaDon,
            @RequestParam(value = "sdt", required = false) String sdt,
            @RequestParam(value = "trangThai", required = false) String trangThai,
            @RequestParam(value = "ghiChu", required = false) String ghiChu
    ) {
        // Gọi service để lấy danh sách hóa đơn theo các tham số
        return hoaDonService.getHoaDon(pageNumber, pageSize, maHoaDon, sdt, trangThai, ghiChu);
    }


    // add dữ liệu
    @PostMapping
    public ResponseEntity<HoaDon> addhoaDon(@RequestBody HoaDon hoaDon) {
        System.out.println(hoaDon.toString());
        HoaDon newHoaDon = hoaDonService.add(hoaDon);
        return ResponseEntity.ok(newHoaDon);
    }

    // update hóa đơn theo id
    @PutMapping("/{id}")
    public ResponseEntity<HoaDon> updatehoaDon(@PathVariable UUID id, @RequestBody HoaDon hoaDonDetail) {
        HoaDon updateHoaDon = hoaDonService.update(id, hoaDonDetail);
        return ResponseEntity.ok(updateHoaDon);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable("id") UUID id) {
        try {
            HoaDon hoaDon = hoaDonService.findById(id); // Gọi service để tìm hóa đơn
            if (hoaDon == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hóa đơn không tồn tại");
            }
            return ResponseEntity.ok(hoaDon); // Trả về hóa đơn nếu tìm thấy
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi tìm hóa đơn: " + e.getMessage());
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<HoaDon> updateStatushoaDon(@PathVariable UUID id) {
        HoaDon updateHoaDon = hoaDonService.updateCancel(id);
        return ResponseEntity.ok(updateHoaDon);
    }

    // delete nhân viên theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoaDon(@PathVariable UUID id) {
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

    @GetMapping("/unpaid")
    public long getUnpaid(@RequestParam String timePeriod, @RequestParam String startDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDateTime startOfPeriod = null;
        LocalDateTime endOfPeriod = null;

        if ("day".equalsIgnoreCase(timePeriod)) {
            startOfPeriod = start.atStartOfDay();
            endOfPeriod = start.atTime(23, 59, 59);
        } else if ("week".equalsIgnoreCase(timePeriod)) {
            startOfPeriod = start.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
            endOfPeriod = start.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY)).atTime(23, 59, 59);
        } else if ("month".equalsIgnoreCase(timePeriod)) {
            startOfPeriod = start.withDayOfMonth(1).atStartOfDay();  // Ngày đầu tháng
            endOfPeriod = start.withDayOfMonth(start.lengthOfMonth()).atTime(23, 59, 59);  // Ngày cuối tháng
        } else {
            throw new IllegalArgumentException("Invalid time period. Must be 'day', 'week', or 'month'.");
        }
        // In ra giá trị của startOfPeriod và endOfPeriod
        System.out.println("Start of period: " + startOfPeriod);
        System.out.println("End of period: " + endOfPeriod);
        return hoaDonService.countUnpaid(timePeriod, startOfPeriod, endOfPeriod);
    }

    // API lấy tổng số hóa đơn đã thanh toán theo thời gian (ngày, tuần hoặc tháng)
    @GetMapping("/paid")
    public long getPaid(@RequestParam String timePeriod, @RequestParam String startDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDateTime startOfPeriod = null;
        LocalDateTime endOfPeriod = null;

        if ("day".equalsIgnoreCase(timePeriod)) {
            startOfPeriod = start.atStartOfDay();
            endOfPeriod = start.atTime(23, 59, 59);
        } else if ("week".equalsIgnoreCase(timePeriod)) {
            startOfPeriod = start.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
            endOfPeriod = start.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY)).atTime(23, 59, 59);
        } else if ("month".equalsIgnoreCase(timePeriod)) {
            startOfPeriod = start.withDayOfMonth(1).atStartOfDay();  // Ngày đầu tháng
            endOfPeriod = start.withDayOfMonth(start.lengthOfMonth()).atTime(23, 59, 59);  // Ngày cuối tháng
        } else {
            throw new IllegalArgumentException("Invalid time period. Must be 'day', 'week', or 'month'.");
        }

        return hoaDonService.countPaid(timePeriod, startOfPeriod, endOfPeriod);
    }

    // API lấy tổng số hóa đơn đã hủy theo thời gian (ngày, tuần hoặc tháng)
    @GetMapping("/cancelled")
    public long getCancelled(@RequestParam String timePeriod, @RequestParam String startDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDateTime startOfPeriod = null;
        LocalDateTime endOfPeriod = null;

        if ("day".equalsIgnoreCase(timePeriod)) {
            startOfPeriod = start.atStartOfDay();
            endOfPeriod = start.atTime(23, 59, 59);
        } else if ("week".equalsIgnoreCase(timePeriod)) {
            startOfPeriod = start.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
            endOfPeriod = start.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY)).atTime(23, 59, 59);
        } else if ("month".equalsIgnoreCase(timePeriod)) {
            startOfPeriod = start.withDayOfMonth(1).atStartOfDay();  // Ngày đầu tháng
            endOfPeriod = start.withDayOfMonth(start.lengthOfMonth()).atTime(23, 59, 59);  // Ngày cuối tháng
        } else {
            throw new IllegalArgumentException("Invalid time period. Must be 'day', 'week', or 'month'.");
        }

        return hoaDonService.countCancelled(timePeriod, startOfPeriod, endOfPeriod);
    }


//    @GetMapping("/total-revenue")
//    public Double getTotalRevenue() {
//        return hoaDonService.calculateTotalRevenue();
//    }

    @GetMapping("/revenue")
    public BigDecimal getTotalRevenue(@RequestParam("timePeriod") String timePeriod) {
        return hoaDonService.calculateRevenueByDateRange(timePeriod);
    }

    // Lấy doanh thu theo thời gian (ngày, tháng, năm)
    // Lấy tổng tiền theo ngày
//    @GetMapping("/tongsotien/ngay")
//    public ResponseEntity<Double> getDoanhThuTheoNgay(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
//        Double doanhThu = hoaDonService.getDoanhThuTheoNgay(startDate, endDate);
//        return ResponseEntity.ok(doanhThu);
//    }
//
//    // Lấy tổng tiền theo tháng
//    @GetMapping("/tongsotien/thang")
//    public ResponseEntity<Double> getDoanhThuTheoThang(@RequestParam String month) {
//        Double doanhThu = hoaDonService.getDoanhThuTheoThang(month);
//        return ResponseEntity.ok(doanhThu);
//    }
//
//    // Lấy tổng tiền theo năm
//    @GetMapping("/tongsotien/nam")
//    public ResponseEntity<Double> getDoanhThuTheoNam(@RequestParam String year) {
//        Double doanhThu = hoaDonService.getDoanhThuTheoNam(year);
//        return ResponseEntity.ok(doanhThu);
//    }

    @GetMapping("/total-revenue")
//    public ResponseEntity<Double> getTotalRevenueForDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        Double totalRevenue = hoaDonService.getTotalRevenueForDay(date);
//        return ResponseEntity.ok(totalRevenue);
//    }
    public List<Map<String, Object>> getRevenueForPeriod(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return hoaDonService.getRevenueForPeriod(startDate, endDate);
    }

    @GetMapping("/bill/today")
    public ResponseEntity<List<HoaDon>> getTodayHoaDon() {
        List<HoaDon> hoaDons = hoaDonService.getHoaDonByToday();
        if (hoaDons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(hoaDons);
    }
}
