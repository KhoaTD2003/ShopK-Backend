package com.example.demo.Repositories;

import com.example.demo.Entities.DanhGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, UUID> {
    Page<DanhGia> findAll(Pageable pageable);
}
