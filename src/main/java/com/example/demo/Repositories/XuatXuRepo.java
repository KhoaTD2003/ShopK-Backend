package com.example.demo.Repositories;

import com.example.demo.Entities.XuatXu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface XuatXuRepo extends JpaRepository<XuatXu, UUID> {
}
