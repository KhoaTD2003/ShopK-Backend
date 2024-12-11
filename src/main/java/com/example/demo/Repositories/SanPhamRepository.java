package com.example.demo.Repositories;

import com.example.demo.Dtos.SanPhamAdminDto;
import com.example.demo.Dtos.SanPhamDto;
import com.example.demo.Entities.GiamGia;
import com.example.demo.Entities.NguoiDung;
import com.example.demo.Entities.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {

    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id ,s.tenSP, s.maSP, s.giaBan, s.anh,s.moTa,s.soLuongTon,s.thuongHieu,s.theLoai,s.size,s.mauSac) " +
            "FROM SanPham s")
    Page<SanPhamDto> findAllProductDetails(Pageable pageable);
//,s.theLoai,s.size,s.mauSac,s.chatLieu,s.xuatXu
    // Trả về danh sách sản phẩm sắp xếp tăng dần theo giá
    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id,s.tenSP, s.maSP, s.giaBan, s.anh,s.moTa,s.soLuongTon,s.thuongHieu,s.theLoai,s.size,s.mauSac) " +
            "FROM SanPham s " +
            "ORDER BY s.giaBan ASC")
    Page<SanPhamDto> findAllProductDetailsSortedByPriceAsc(Pageable pageable);

    // Trả về danh sách sản phẩm sắp xếp giảm dần theo giá
    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id,s.tenSP, s.maSP, s.giaBan, s.anh,s.moTa,s.soLuongTon,s.thuongHieu,s.theLoai,s.size,s.mauSac) " +
            "FROM SanPham s " +
            "ORDER BY s.giaBan DESC")
    Page<SanPhamDto> findAllProductDetailsSortedByPriceDesc(Pageable pageable);

//    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.tenSP, s.maSP, s.giaBan, s.anh) " +
//            "FROM SanPham s " +
//            "WHERE LOWER(s.tenSP) LIKE LOWER(CONCAT('%', :tenSP, '%'))")
//    List<SanPhamDto> findProductsByName(@Param("tenSP") String tenSP);

//    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id,s.tenSP, s.maSP, s.giaBan, s.anh,s.moTa, s.thuongHieu,s.theLoai,s.size,s.mauSac) " +
//            "FROM SanPham s " +
//            "WHERE (:tenSP IS NULL OR s.tenSP LIKE %:tenSP%) " +
//            "ORDER BY " +
//            "CASE WHEN :sortOrder = 'asc' THEN s.giaBan END ASC, " +
//            "CASE WHEN :sortOrder = 'desc' THEN s.giaBan END DESC")
//    Page<SanPhamDto> searchAndSortProductsByName(
//            @Param("tenSP") String tenSP,
//            @Param("sortOrder") String sortOrder,
//            Pageable pageable);
@Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id, s.tenSP, s.maSP, s.giaBan, s.anh, s.moTa,s.soLuongTon, s.thuongHieu, s.theLoai, s.size, s.mauSac) " +
        "FROM SanPham s " +
        "WHERE (:tenSP IS NULL OR s.tenSP LIKE %:tenSP%)")
Page<SanPhamDto> searchAndSortProductsByName(
        @Param("tenSP") String tenSP,
        Pageable pageable);


    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id,s.tenSP, s.maSP, s.giaBan, s.anh, s.moTa, s.soLuongTon,s.thuongHieu,s.theLoai,s.size,s.mauSac) " +
            "FROM SanPham s " +
            "JOIN s.thuongHieu th " +
            "WHERE (:thuongHieu IS NULL OR th.ten LIKE %:thuongHieu%)")
    Page<SanPhamDto> findByThuongHieu(@Param("thuongHieu") String thuongHieu,Pageable pageable);

    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id,s.tenSP, s.maSP, s.giaBan, s.anh, s.moTa, s.soLuongTon,s.thuongHieu,s.theLoai,s.size,s.mauSac) " +
            "FROM SanPham s " +
            "JOIN s.theLoai tl " +
            "WHERE (:theLoai IS NULL OR tl.ten LIKE %:theLoai%)")
    Page<SanPhamDto> findByTheLoai(@Param("theLoai") String theLoai,Pageable pageable);

    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id,s.tenSP, s.maSP, s.giaBan, s.anh, s.moTa,s.soLuongTon, s.thuongHieu, s.theLoai,s.size,s.mauSac) " +
            "FROM SanPham s " +
            "WHERE s.maSP = :maSP")
    SanPhamDto findByMaSP(@Param("maSP") String maSP);

    // Tìm sản phẩm theo khoảng giá
    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id,s.tenSP, s.maSP, s.giaBan, s.anh, s.moTa,s.soLuongTon, s.thuongHieu, s.theLoai,s.size,s.mauSac) " +
            "FROM SanPham s " +
            "WHERE s.giaBan BETWEEN :minPrice AND :maxPrice")
    Page<SanPhamDto> findByPriceBetween(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, Pageable pageable);

    // Tìm sản phẩm theo màu sắc
    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id,s.tenSP, s.maSP, s.giaBan, s.anh, s.moTa,s.soLuongTon, s.thuongHieu, s.theLoai,s.size,s.mauSac) " +
            "FROM SanPham s " +
            "JOIN s.mauSac m " +
            "WHERE (:mauSac IS NULL OR m.ten LIKE %:mauSac%)")
    Page<SanPhamDto> findByMauSac(@Param("mauSac") String mauSac, Pageable pageable);

    // Tìm sản phẩm theo kích thước
    @Query("SELECT new com.example.demo.Dtos.SanPhamDto(s.id,s.tenSP, s.maSP, s.giaBan, s.anh, s.moTa, s.soLuongTon,s.thuongHieu, s.theLoai,s.size,s.mauSac) " +
            "FROM SanPham s " +
            "JOIN s.size sz " +
            "WHERE (:size IS NULL OR sz.ten LIKE %:size%)")
    Page<SanPhamDto> findBySize(@Param("size") String size, Pageable pageable);



    //SAN PHAM ADMIN


    @Query("SELECT new com.example.demo.Dtos.SanPhamAdminDto(sp.id, sp.maSP, sp.tenSP, sp.giaBan, sp.soLuongTon, sp.moTa, sp.anh, "
            + "th.ten, sz.ten, ms.ten, cl.ten, xx.ten, tl.ten, sp.trangThai,sp.ngayTao) "
            + "FROM SanPham sp "
            + "JOIN sp.thuongHieu th "
            + "JOIN sp.size sz "
            + "JOIN sp.mauSac ms "
            + "JOIN sp.chatLieu cl "
            + "JOIN sp.xuatXu xx "
            + "JOIN sp.theLoai tl "
            + "ORDER BY sp.ngayTao DESC ") // Sắp xếp theo ngày tạo
    Page<SanPhamAdminDto> findAllProduct(Pageable pageable);

    void delete(SanPham sanPham);

//    @Query("SELECT MAX(p.maSP) FROM SanPham p")
//    String getMaxProductCode();
//
//    @Query("SELECT MAX(CAST(SUBSTRING(p.maSP, 3, LENGTH(p.maSP)) AS INT)) FROM SanPham p")
//    Integer getMaxProductNumber();
//
//    @Query("SELECT p.maSP FROM SanPham p WHERE p.maSP LIKE 'SP%' ORDER BY p.maSP DESC")
//    List<String> getAllProductCodes();

    @Query(value = "SELECT MAX(CAST(SUBSTRING(masanpham, 3, LEN(masanpham)) AS INT)) FROM SanPham WHERE masanpham LIKE 'SP%'", nativeQuery = true)
    Integer getMaxProductCode();


    boolean existsByMaSP(String maSP);

//    Optional<SanPham> findByMa(String ma);

}
