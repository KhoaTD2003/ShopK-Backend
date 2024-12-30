package com.example.demo.Repositories;

import com.example.demo.Dtos.NguoiDungDto;
import com.example.demo.Entities.NguoiDung;
import com.example.demo.Entities.TaiKhoan;
import org.apache.catalina.User;
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
public interface NguoiDungRepository extends JpaRepository<NguoiDung, UUID> {

    @Query("SELECT new com.example.demo.Dtos.NguoiDungDto(n.maNguoiDung, n.hoTen, n.diaChi, n.email, n.sdt,n.taiKhoan.id,n.trangThai) " +
            "FROM NguoiDung n WHERE n.taiKhoan.id = :idTaiKhoan")
    List<NguoiDungDto> findNguoiDungDtoByIdTaiKhoan(@Param("idTaiKhoan") UUID idTaiKhoan);

    @Query("SELECT u FROM NguoiDung u WHERE u.taiKhoan.id = :idTaiKhoan")
    List<NguoiDung> findByTaiKhoanId(@Param("idTaiKhoan") UUID idTaiKhoan);


    default NguoiDung findFirstByTaiKhoanId(UUID idTaiKhoan) {
        List<NguoiDung> users = findByTaiKhoanId(idTaiKhoan);
        return users.isEmpty() ? null : users.get(0); // Trả về người dùng đầu tiên hoặc null nếu không có
    }


//    @Query("SELECT n FROM NguoiDung n WHERE n.taiKhoan.id = :idTaiKhoan")
//    NguoiDung findByIdTaiKhoan(@Param("idTaiKhoan") UUID idTaiKhoan);

    //    List<NguoiDung> findByTaiKhoanRole(String role);
    @Query("SELECT nd FROM NguoiDung nd WHERE nd.taiKhoan.role = :role")
    Page<NguoiDung> findByTaiKhoan_Role(String role, Pageable pageable);


    // 1. Lấy người dùng theo vai trò (role)
    @Query("SELECT nd FROM NguoiDung nd WHERE nd.taiKhoan.role = :role")
    Page<NguoiDung> findByRole(@Param("role") String role, Pageable pageable);

    // 2. Lấy người dùng theo tên hoặc số điện thoại và vai trò
    @Query("SELECT nd FROM NguoiDung nd WHERE (nd.hoTen LIKE %:hoTen% OR nd.sdt LIKE %:sdt%) AND nd.taiKhoan.role = :role")
    Page<NguoiDung> findByHoTenOrSdtAndRole(@Param("hoTen") String hoTen, @Param("sdt") String sdt, @Param("role") String role, Pageable pageable);

    // 3. Lấy người dùng theo trạng thái và vai trò
        @Query("SELECT nd FROM NguoiDung nd WHERE nd.trangThai = :trangThai AND nd.taiKhoan.role = :role")
        Page<NguoiDung> findByTrangThaiAndRole(@Param("trangThai") Boolean trangThai, @Param("role") String role, Pageable pageable);

    @Query("SELECT u FROM NguoiDung u WHERE (u.hoTen = :hoTen OR u.sdt = :sdt) AND u.trangThai = :trangThai AND u.taiKhoan.role = :role")
    Page<NguoiDung> findByHoTenOrSdtAndTrangThaiAndRole(@Param("hoTen") String hoTen, @Param("sdt") String sdt, @Param("trangThai") Boolean trangThai, @Param("role") String role, Pageable pageable);

    void delete(NguoiDung nguoiDung);

    public NguoiDung findByEmail(String email);

    public NguoiDung findBySdt(String sdt);
}
