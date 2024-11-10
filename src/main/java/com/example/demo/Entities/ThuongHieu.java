package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

    @Getter
    @Setter
    @Entity
    @Table(name = "ThuongHieu")
    public class ThuongHieu {

        @Id
        @GeneratedValue
        private UUID id;

        @Column(nullable = true, unique = true)
        private String ma;

        @Column(nullable = true)
        private String ten;

        // Getters and setters


}
