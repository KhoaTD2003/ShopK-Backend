package com.example.demo.Repositories;

import com.example.demo.Dtos.SanPhamDto;
import com.example.demo.Entities.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {

    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.tenSP, s.maSP, s.giaBan, s.anh,s.moTa) " +
            "FROM SanPham s")
    List<SanPhamDto> findAllProductDetails();

    // Trả về danh sách sản phẩm sắp xếp tăng dần theo giá
    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.tenSP, s.maSP, s.giaBan, s.anh,s.moTa) " +
            "FROM SanPham s " +
            "ORDER BY s.giaBan ASC")
    List<SanPhamDto> findAllProductDetailsSortedByPriceAsc();

    // Trả về danh sách sản phẩm sắp xếp giảm dần theo giá
    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.tenSP, s.maSP, s.giaBan, s.anh,s.moTa) " +
            "FROM SanPham s " +
            "ORDER BY s.giaBan DESC")
    List<SanPhamDto> findAllProductDetailsSortedByPriceDesc();

//    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.tenSP, s.maSP, s.giaBan, s.anh) " +
//            "FROM SanPham s " +
//            "WHERE LOWER(s.tenSP) LIKE LOWER(CONCAT('%', :tenSP, '%'))")
//    List<SanPhamDto> findProductsByName(@Param("tenSP") String tenSP);

    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.tenSP, s.maSP, s.giaBan, s.anh,s.moTa) " +
            "FROM SanPham s " +
            "WHERE (:tenSP IS NULL OR s.tenSP LIKE %:tenSP%) " +
            "ORDER BY " +
            "CASE WHEN :sortOrder = 'asc' THEN s.giaBan END ASC, " +
            "CASE WHEN :sortOrder = 'desc' THEN s.giaBan END DESC")
    List<SanPhamDto> searchAndSortProductsByName(
            @Param("tenSP") String tenSP,
            @Param("sortOrder") String sortOrder);


}
