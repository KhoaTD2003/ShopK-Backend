package com.example.demo.Repositories;

import com.example.demo.Entities.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TheLoaiRepo extends JpaRepository<TheLoai, UUID> {

    @Query(value = "SELECT MAX(CAST(SUBSTRING(ma, 3, LEN(ma)) AS INT)) FROM TheLoai WHERE ma LIKE 'TL%'", nativeQuery = true)
    Integer getMaxCategoryCode();

    boolean existsByMa(String ma);

    boolean existsByTen(String ten); // Tự động sinh truy vấn kiểm tra tên

}
