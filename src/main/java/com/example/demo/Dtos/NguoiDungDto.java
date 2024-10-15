        package com.example.demo.Dtos;

        import com.example.demo.Entities.TaiKhoan;
        import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.Setter;

        import java.util.Date;
        import java.util.UUID;

        @AllArgsConstructor
        @Getter
        @Setter
        @NoArgsConstructor
        public class NguoiDungDto {

                private String maNguoiDung;

                private String hoTen;

                private String diaChi;

                private String email;

                private String sdt;

                private UUID idTaiKhoan; // Thay đổi để lấy ID tài khoản

        }
