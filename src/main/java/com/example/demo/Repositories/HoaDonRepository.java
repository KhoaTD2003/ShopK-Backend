package com.example.demo.Repositories;

import com.example.demo.Entities.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {

//    List<HoaDon> findBySdt(String sdt);

    @Query("SELECT h FROM HoaDon h WHERE h.sdt = :sdt ORDER BY h.ngayTao DESC")
    List<HoaDon> findLatestBySdt(@Param("sdt") String sdt);

    @Query("SELECT h FROM HoaDon h WHERE h.maHoaDon LIKE %:maHoaDon% OR h.sdt LIKE %:sdt%")
    Page<HoaDon> findByMaHoaDonOrSdtContaining(@Param("maHoaDon") String maHoaDon,
                                               @Param("sdt") String sdt,
                                               Pageable pageable);

    @Query("SELECT h FROM HoaDon h WHERE " +
            "(COALESCE(:trangThai, '') = '' OR h.trangThai = :trangThai) AND " +
            "(COALESCE(:ghiChu, '') = '' OR h.ghiChu = :ghiChu)")
    Page<HoaDon> findByTrangThaiAndGhiChuContaining(
            @Param("trangThai") String trangThai,
            @Param("ghiChu") String ghiChu,
            Pageable pageable);


    // Tổng số hóa đơn với trạng thái "Chưa Thanh Toán" theo ngày
    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.trangThai = 'Chua Thanh Toán' AND h.ngayTao BETWEEN :startOfDay AND :endOfDay")
    long countUnpaidByDay(LocalDateTime startOfDay, LocalDateTime endOfDay);

    // Tổng số hóa đơn với trạng thái "Chưa Thanh Toán" theo tuần
    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.trangThai = 'Chua Thanh Toán' AND h.ngayTao BETWEEN :startOfWeek AND :endOfWeek")
    long countUnpaidByWeek(LocalDateTime startOfWeek, LocalDateTime endOfWeek);

    // Tổng số hóa đơn "Chưa Thanh Toán" theo tháng
    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.trangThai = 'Chua Thanh Toán' AND FUNCTION('MONTH', h.ngayTao) = :month AND FUNCTION('YEAR', h.ngayTao) = :year")
    long countUnpaidByMonth(@Param("month") int month, @Param("year") int year);

    // Tổng số hóa đơn với trạng thái "Đã Thanh Toán" theo ngày
    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.trangThai = 'Ðã Thanh Toán' AND h.ngayTao BETWEEN :startOfDay AND :endOfDay")
    long countPaidByDay(LocalDateTime startOfDay, LocalDateTime endOfDay);

    // Tổng số hóa đơn với trạng thái "Đã Thanh Toán" theo tuần
    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.trangThai = 'Ðã Thanh Toán' AND h.ngayTao BETWEEN :startOfWeek AND :endOfWeek")
    long countPaidByWeek(LocalDateTime startOfWeek, LocalDateTime endOfWeek);

//    // Tổng số hóa đơn với trạng thái "Đã Thanh Toán" theo tháng
//    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.trangThai = 'Da Thanh Toan' AND FUNCTION('MONTH', h.ngayTao) = FUNCTION('MONTH', :startOfMonth) AND FUNCTION('YEAR', h.ngayTao) = FUNCTION('YEAR', :startOfMonth)")
//    long countPaidByMonth(LocalDateTime startOfMonth);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.trangThai = 'Ðã Thanh Toán' AND FUNCTION('MONTH', h.ngayTao) = :month AND FUNCTION('YEAR', h.ngayTao) = :year")
    long countPaidByMonth(@Param("month") int month, @Param("year") int year);

    // Tổng số hóa đơn với trạng thái "Đã Hủy" theo ngày
    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.trangThai = 'Cancle' AND h.ngayTao BETWEEN :startOfDay AND :endOfDay")
    long countCancelledByDay(LocalDateTime startOfDay, LocalDateTime endOfDay);

    // Tổng số hóa đơn với trạng thái "Đã Hủy" theo tuần
    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.trangThai = 'Cancle' AND h.ngayTao BETWEEN :startOfWeek AND :endOfWeek")
    long countCancelledByWeek(LocalDateTime startOfWeek, LocalDateTime endOfWeek);

    // Tổng số hóa đơn với trạng thái "Đã Hủy" theo tháng
    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.trangThai = 'Cancle' AND FUNCTION('MONTH', h.ngayTao) = FUNCTION('MONTH', :startOfMonth) AND FUNCTION('YEAR', h.ngayTao) = FUNCTION('YEAR', :startOfMonth)")
    long countCancelledByMonth(LocalDateTime startOfMonth);


    // Tổng doanh thu với trạng thái "Đã Thanh Toán"
    @Query("SELECT SUM(CAST(h.tongTien AS double)) FROM HoaDon h WHERE h.trangThai = 'Đã Thanh Toán'")
    Double calculateTotalRevenue();


    // Tính tổng doanh thu theo trạng thái và khoảng thời gian (ngày, tuần, tháng)
     @Query("SELECT SUM(CAST(hd.tongTien AS java.math.BigDecimal)) FROM HoaDon hd WHERE hd.trangThai = 'Đã thanh toán' AND hd.ngayTao BETWEEN :startDate AND :endDate")
    BigDecimal calculateRevenueByStatusAndDateRange(LocalDateTime startDate, LocalDateTime endDate);

}
