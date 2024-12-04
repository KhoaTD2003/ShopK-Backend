package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "XuatXu")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class XuatXu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ma", length = 255)  // Đặt độ dài tối đa cho cột ma là 255
    private String ma;

    @Column(name = "ten")
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
