package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "NguoiDung")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "manguoidung", unique = true, nullable = true)
    private String maNguoiDung;

    @OneToOne(fetch = FetchType.LAZY) // Tham chiếu đến bảng TaiKhoan
    @JoinColumn(name = "id_taikhoan")
    @JsonIgnore // Thêm annotation này
    private TaiKhoan taiKhoan;

    @Column(name = "hoten")
    private String hoTen;

    @Column(name = "namsinh")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")

    private Date namSinh;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt", length = 10)
    private String sdt;

    @Column(name = "trangthai")
    private Boolean trangThai = true;

    @Column(name = "ngaytao")
    private LocalDateTime ngayTao;

    @PrePersist
    public void prePersist() {
        if (this.ngayTao == null) {
            this.ngayTao = LocalDateTime.now(); // Thiết lập giá trị createdAt khi thêm mới
        }
    }
}
