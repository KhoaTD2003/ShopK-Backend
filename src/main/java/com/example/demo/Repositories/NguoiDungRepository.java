package com.example.demo.Repositories;

import com.example.demo.Dtos.NguoiDungDto;
import com.example.demo.Entities.NguoiDung;
import com.example.demo.Entities.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, UUID> {

    @Query("SELECT new com.example.demo.Dtos.NguoiDungDto(n.maNguoiDung, n.hoTen, n.diaChi, n.email, n.sdt,n.taiKhoan.id) " +
            "FROM NguoiDung n WHERE n.taiKhoan.id = :idTaiKhoan")
    List<NguoiDungDto> findByIdTaiKhoan(@Param("idTaiKhoan") UUID idTaiKhoan);

    @Query("SELECT u FROM NguoiDung u WHERE u.taiKhoan.id = :idTaiKhoan")
    List<NguoiDung> findByTaiKhoanId(@Param("idTaiKhoan") UUID idTaiKhoan);

    Optional<NguoiDung> findByMaNguoiDung(String maNguoiDung);

    NguoiDung findByEmail(String email);

    default NguoiDung findFirstByTaiKhoanId(UUID idTaiKhoan) {
        List<NguoiDung> users = findByTaiKhoanId(idTaiKhoan);
        return users.isEmpty() ? null : users.get(0); // Trả về người dùng đầu tiên hoặc null nếu không có
    }
}
