package com.example.demo.Repositories;

import com.example.demo.Entities.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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



}
