package com.example.demo.Repositories;

import com.example.demo.Entities.ChiTietHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, UUID> {
}
