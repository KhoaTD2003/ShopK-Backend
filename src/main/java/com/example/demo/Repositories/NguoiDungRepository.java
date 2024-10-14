package com.example.demo.Repositories;

import com.example.demo.Entities.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, UUID> {
}
