package com.example.demo.Repositories;

import com.example.demo.Entities.GioHang;
import com.example.demo.Entities.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GioHangRepository extends JpaRepository<GioHang, UUID> {

}
