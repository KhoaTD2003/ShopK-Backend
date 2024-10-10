//package com.example.demo.Entities;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Date;
//import java.util.UUID;
//
//    @Table(name = "KhachHang")
//    @Entity
//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public class NguoiDung {
//        @Id
//        @GeneratedValue(strategy = GenerationType.UUID)
//        @Column(name = "id")
//        private UUID id;
//        @Column(name = "makh")
//        private String maKH;
//        @Column(name = "hoten")
//        private  String hoTen;
//        @Column(name = "namsinh")
//        private Date namSinh;
//        @Column(name = "sdt")
//        private String soDT;
//        @Column(name = "email")
//        private String email;
//        @Column(name = "trangthai")
//        private String trangThai;
//}
