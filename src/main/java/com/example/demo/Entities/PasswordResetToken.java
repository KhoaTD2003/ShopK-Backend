//package com.example.demo.Entities;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//public class PasswordResetToken {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Long id;
//
//        private String token;
//
//        @OneToOne
//        private TaiKhoan taiKhoan;
//
//        private LocalDateTime expiryDate;
//
//        // Constructor, getter, setter
//
//
//}
