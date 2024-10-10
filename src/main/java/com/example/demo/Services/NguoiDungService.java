package com.example.demo.Services;


import com.example.demo.Entities.NguoiDung;
import com.example.demo.Repositories.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

    @Service
    public class NguoiDungService {

        @Autowired
        private NguoiDungRepository nguoiDungRepository;

        // Lấy tất cả người dùng
        public List<NguoiDung> getAllNguoiDung() {
            return nguoiDungRepository.findAll();
        }

        // Lấy người dùng theo ID
        public Optional<NguoiDung> getNguoiDungById(UUID id) {
            return nguoiDungRepository.findById(id);
        }

        // Thêm mới người dùng
        public NguoiDung createNguoiDung(NguoiDung nguoiDung) {
            return nguoiDungRepository.save(nguoiDung);
        }

        // Cập nhật người dùng
        public NguoiDung updateNguoiDung(UUID id, NguoiDung updatedNguoiDung) {
            Optional<NguoiDung> existingNguoiDung = nguoiDungRepository.findById(id);
            if (existingNguoiDung.isPresent()) {
                NguoiDung nguoiDung = existingNguoiDung.get();
                nguoiDung.setMaNguoiDung(updatedNguoiDung.getMaNguoiDung());
                nguoiDung.setHoTen(updatedNguoiDung.getHoTen());
                nguoiDung.setNamSinh(updatedNguoiDung.getNamSinh());
                nguoiDung.setDiaChi(updatedNguoiDung.getDiaChi());
                nguoiDung.setEmail(updatedNguoiDung.getEmail());
                nguoiDung.setSdt(updatedNguoiDung.getSdt());
                nguoiDung.setTrangThai(updatedNguoiDung.getTrangThai());
                nguoiDung.setTaiKhoan(updatedNguoiDung.getTaiKhoan());
                return nguoiDungRepository.save(nguoiDung);
            }
            return null;  // Có thể ném ngoại lệ nếu không tìm thấy người dùng
        }

        // Xóa người dùng
        public void deleteNguoiDung(UUID id) {
            nguoiDungRepository.deleteById(id);
        }
    }


