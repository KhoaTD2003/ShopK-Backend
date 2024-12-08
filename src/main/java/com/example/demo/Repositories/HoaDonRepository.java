package com.example.demo.Repositories;

import com.example.demo.Entities.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {

//    List<HoaDon> findBySdt(String sdt);

    @Query("SELECT h FROM HoaDon h WHERE h.sdt = :sdt ORDER BY h.ngayTao DESC")
    List<HoaDon> findLatestBySdt(@Param("sdt") String sdt);
}
