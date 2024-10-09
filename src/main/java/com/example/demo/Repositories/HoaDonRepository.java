package com.example.demo.Repositories;

import com.example.demo.Entities.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {
}
