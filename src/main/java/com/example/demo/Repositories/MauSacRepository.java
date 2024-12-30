package com.example.demo.Repositories;

import com.example.demo.Entities.MauSac;
import com.example.demo.Entities.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, UUID> {
    @Query(value = "SELECT MAX(CAST(SUBSTRING(ma, 3, LEN(ma)) AS INT)) FROM MauSac WHERE ma LIKE 'MS%'", nativeQuery = true)
    Integer getMaxColorCode();

    boolean existsByMa(String ma);

    boolean existsByTen(String ten); // Tự động sinh truy vấn kiểm tra tên

    Page<MauSac> findByTenContaining(String ten, Pageable pageable);

}
