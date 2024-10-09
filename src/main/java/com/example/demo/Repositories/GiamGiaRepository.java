package com.example.demo.Repositories;

import com.example.demo.Entities.GiamGia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GiamGiaRepository extends JpaRepository<GiamGia, UUID> {
}
