package com.example.demo.Repositories;

import com.example.demo.Entities.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, UUID> {
    //login
    TaiKhoan findByTenTaiKhoan(String tenTaiKhoan);

    TaiKhoan findByEmail(String email);

    TaiKhoan findBySdt(String sdt);  // Thêm hàm để tìm theo số điện thoại

    TaiKhoan findByResetToken(String resetToken);

    //admin
//    @Query("SELECT t FROM TaiKhoan t WHERE t.role = :role")
//    List<TaiKhoan> findByRole(@Param("role") String role);


//    @Query("SELECT new com.example.demo.Dtos.TaiKhoanUsersDto(t.id, t.tenTaiKhoan, t.email, t.sdt, t.role, t.trangThai, " +
//            "nd.maNguoiDung, nd.hoTen, nd.namSinh, nd.diaChi, nd.email, nd.sdt, nd.trangThai) " +
//            "FROM TaiKhoan t LEFT JOIN NguoiDung nd ON t.id = nd.taiKhoan.id " +
//            "WHERE t.role = :role")
//    List<TaiKhoanUsersDto> findTaiKhoanUsersByRole(@Param("role") String role);
//
    void delete(TaiKhoan taiKhoan);


    //admin
    // Tìm kiếm tất cả tài khoản với phân trang
    Page<TaiKhoan> findByRole(String role, Pageable pageable);

    // Tìm kiếm tài khoản theo tên/số điện thoại và role
    @Query("SELECT t FROM TaiKhoan t WHERE (t.tenTaiKhoan LIKE %:tenTaiKhoan% OR t.sdt LIKE %:sdt%) AND t.role = :role")
    Page<TaiKhoan> findTenTaiKhoanContainingOrSdtContainingByRole(
            @Param("tenTaiKhoan") String tenTaiKhoan,
            @Param("sdt") String sdt,
            @Param("role") String role,
            Pageable pageable);

    // Tìm kiếm tài khoản theo trạng thái và role
    @Query("SELECT t FROM TaiKhoan t WHERE t.trangThai = :trangThai AND t.role = :role")
    Page<TaiKhoan> findByTrangThaiAndRole(
            @Param("trangThai") boolean trangThai,
            @Param("role") String role,
            Pageable pageable);

//    // Tìm kiếm tài khoản theo tên hoặc số điện thoại và trạng thái
//    Page<TaiKhoan> findByTenTaiKhoanContainingIgnoreCaseOrSdtContainingAndTrangThai(
//            String tenTaiKhoan, String sdt, boolean trangThai, Pageable pageable);
}
