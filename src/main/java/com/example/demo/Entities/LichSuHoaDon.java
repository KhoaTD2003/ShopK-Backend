package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LichSuHoaDon")
public class LichSuHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "hoadon_id", referencedColumnName = "id", nullable = false) // không cho phép null
    private HoaDon hoaDon; // Tham chiếu đến bảng HoaDon

    @Column(columnDefinition = "TEXT")
    private String moTa; // Mô tả thay đổi

    @Column
    private Boolean trangThai; // Trạng thái sau thay đổi (có thể lưu 0 = hủy, 1 = hoàn thành)


}
