package com.example.demo.Repositories;

import com.example.demo.Entities.GiamGia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GiamGiaRepository extends JpaRepository<GiamGia, UUID> {

    Optional<GiamGia> findByMa(String ma);

    @Modifying
    @Transactional
    @Query("UPDATE GiamGia g SET g.soLansd = ?2 WHERE g.id = ?1")
    void updateSoLansd(UUID id, int newSoLansd);
}
