package com.example.demo.Repositories;

import com.example.demo.Dtos.ChiTietHoaDonDto;
import com.example.demo.Dtos.TopSellingProductDTO;
import com.example.demo.Entities.ChiTietHoaDon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<ChiTietHoaDon, UUID> {

    List<ChiTietHoaDon> findByHoaDonId(UUID idHoaDon); // Tìm theo ID hóa đơn

    @Query("SELECT new com.example.demo.Dtos.TopSellingProductDTO( " +
            "c.sanPham.tenSP, SUM(c.soLuong), SUM(c.tongTien)) " +
            "FROM ChiTietHoaDon c " +
            "WHERE c.trangThai = true " + // Lọc các sản phẩm đã thanh toán
            "GROUP BY c.sanPham.tenSP " +
            "ORDER BY SUM(c.soLuong) DESC")
    List<TopSellingProductDTO> findTopSellingProducts(Pageable pageable);

}
