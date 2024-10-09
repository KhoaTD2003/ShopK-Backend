package com.example.demo.Repositories;

import com.example.demo.Entities.PhuongThucTt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhuongThucThanhToanRepository extends JpaRepository<PhuongThucTt, UUID> {
}
