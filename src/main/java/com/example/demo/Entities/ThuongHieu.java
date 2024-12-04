package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

    @Getter
    @Setter
    @Entity
    @Table(name = "ThuongHieu")
    @AllArgsConstructor
    @NoArgsConstructor
    public class ThuongHieu {

        @Id
        @GeneratedValue
        private UUID id;

        @Column(nullable = true, unique = true)
        private String ma;

        @Column(nullable = true)
        private String ten;

        @Column(name = "ngaytao")
        private LocalDateTime ngayTao;


        @PrePersist
        public void prePersist() {
            if (this.ngayTao == null) {
                this.ngayTao = LocalDateTime.now(); // Thiết lập giá trị createdAt khi thêm mới
            }
        }

}
