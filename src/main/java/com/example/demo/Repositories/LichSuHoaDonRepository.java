package com.example.demo.Repositories;

import com.example.demo.Entities.LichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LichSuHoaDonRepository extends JpaRepository<LichSuHoaDon, UUID> {
}
