package com.example.demo.Repositories;

import com.example.demo.Entities.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TheLoaiRepo extends JpaRepository<TheLoai, UUID> {
}
