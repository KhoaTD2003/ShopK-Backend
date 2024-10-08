package com.example.demo.Repositories;

import com.example.demo.Entities.Hang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HangRepository extends JpaRepository<Hang, UUID> {
}
