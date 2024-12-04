package com.example.demo.Repositories;

import com.example.demo.Entities.XuatXu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface XuatXuRepo extends JpaRepository<XuatXu, UUID> {

    @Query(value = "SELECT MAX(CAST(SUBSTRING(ma, 3, LEN(ma)) AS INT)) FROM XuatXu WHERE ma LIKE 'XX%'", nativeQuery = true)
    Integer getMaxOriginCode();

    boolean existsByMa(String ma);

    boolean existsByTen(String ten); // Tự động sinh truy vấn kiểm tra tên

}
