package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "mahoadon")
    private String maHoaDon;
    @Column(name = "tenkh")
    private String tenKH;
    @Column(name = "tennv")
    private String tenNV;
    @Column(name = "tongtien")
    private String tongTien;
    @Column(name = "tienthu")
    private String tienThu;
    @Column(name = "tienGiam")
    private String tienGiam;
//    @Temporal(TemporalType.DATE)
//    private Date ngayTao;

    @Column(name = "ngayTao")
    private LocalDateTime ngayTao;  // Thêm trường này để lưu thời gian tạo hóa đơn


    @Column(name = "trangthai")
    private String trangThai;

//    @Enumerated(EnumType.STRING)
    @Column(name = "sdt ")
    private String sdt ;

    @Column(name = "ghichu")
    private String ghiChu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_phuongthuctt")
    private PhuongThucTt phuongThucTt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_giamgia")
    private GiamGia giamGia;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_taikhoan")
    private TaiKhoan taiKhoan;

}
