package com.example.demo.Services;


import com.example.demo.Dtos.NguoiDungDto;
import com.example.demo.Entities.NguoiDung;
import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Repositories.NguoiDungRepository;
import jakarta.persistence.EntityNotFoundException;
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

        public List<NguoiDungDto> getNguoiDungByIdTaiKhoan(UUID idTaiKhoan) {
            return nguoiDungRepository.findByIdTaiKhoan(idTaiKhoan);
        }
//
//        public NguoiDung saveOrUpdateNguoiDung(NguoiDungDto nguoiDungDto ,TaiKhoan taiKhoan) {
//            Optional<NguoiDung> existingNguoiDung = nguoiDungRepository.findByMaNguoiDung(nguoiDungDto.getMaNguoiDung());
//
//            NguoiDung nguoiDung;
//            if (existingNguoiDung.isPresent()) {
//                // Nếu người dùng đã tồn tại, cập nhật thông tin
//                nguoiDung = existingNguoiDung.get();
//                nguoiDung.setHoTen(nguoiDungDto.getHoTen());
//                nguoiDung.setDiaChi(nguoiDungDto.getDiaChi());
//                nguoiDung.setEmail(nguoiDungDto.getEmail());
//                nguoiDung.setSdt(nguoiDungDto.getSdt());
//                nguoiDung.setTaiKhoan(taiKhoan); // Gán tài khoản vào người dùng
//
//            } else {
//                // Nếu người dùng chưa tồn tại, tạo mới
//                nguoiDung = new NguoiDung();
//                nguoiDung.setMaNguoiDung(nguoiDungDto.getMaNguoiDung());
//                nguoiDung.setHoTen(nguoiDungDto.getHoTen());
//                nguoiDung.setDiaChi(nguoiDungDto.getDiaChi());
//                nguoiDung.setEmail(nguoiDungDto.getEmail());
//                nguoiDung.setSdt(nguoiDungDto.getSdt());
//                nguoiDung.setTaiKhoan(taiKhoan); // Gán tài khoản vào người dùng
//
//            }
//            // Lưu người dùng vào cơ sở dữ liệu
//            return nguoiDungRepository.save(nguoiDung);
//        }

//        public NguoiDung saveOrUpdateNguoiDung(NguoiDungDto nguoiDungDto) {
//            NguoiDung nguoiDung = new NguoiDung();
//
//            // Gán các giá trị từ DTO vào entity
//            nguoiDung.setMaNguoiDung(nguoiDungDto.getMaNguoiDung());
//            nguoiDung.setHoTen(nguoiDungDto.getHoTen());
//            nguoiDung.setDiaChi(nguoiDungDto.getDiaChi());
//            nguoiDung.setEmail(nguoiDungDto.getEmail());
//            nguoiDung.setSdt(nguoiDungDto.getSdt());
//
//            // Nếu ID tài khoản không null, gán vào tài khoản
//            if (nguoiDungDto.getIdTaiKhoan() != null) {
//                TaiKhoan taiKhoan = new TaiKhoan();
//                taiKhoan.setId(nguoiDungDto.getIdTaiKhoan()); // Gán ID tài khoản
//                nguoiDung.setTaiKhoan(taiKhoan);
//            }
//
//            return nguoiDungRepository.save(nguoiDung); // Lưu người dùng
//        }

        public NguoiDung saveOrUpdateNguoiDung(NguoiDungDto nguoiDungDto) {
            // Tìm người dùng theo ID tài khoản
            NguoiDung existingUser = nguoiDungRepository.findFirstByTaiKhoanId(nguoiDungDto.getIdTaiKhoan());

            if (existingUser != null) {
                // Cập nhật thông tin người dùng
                existingUser.setHoTen(nguoiDungDto.getHoTen());
                existingUser.setDiaChi(nguoiDungDto.getDiaChi());
                existingUser.setEmail(nguoiDungDto.getEmail());
                existingUser.setSdt(nguoiDungDto.getSdt());
                return nguoiDungRepository.save(existingUser); // Cập nhật bản ghi hiện có
            } else {
                // Tạo người dùng mới
                NguoiDung newUser = new NguoiDung();
                newUser.setMaNguoiDung(nguoiDungDto.getMaNguoiDung());
                newUser.setTaiKhoan(new TaiKhoan(nguoiDungDto.getIdTaiKhoan())); // Tạo đối tượng TaiKhoan với ID
                newUser.setHoTen(nguoiDungDto.getHoTen());
                newUser.setDiaChi(nguoiDungDto.getDiaChi());
                newUser.setEmail(nguoiDungDto.getEmail());
                newUser.setSdt(nguoiDungDto.getSdt());
                return nguoiDungRepository.save(newUser); // Lưu người dùng mới
            }
        }

        // Phương thức tìm người dùng theo ID tài khoản
        public NguoiDung findByTaiKhoanId(UUID idTaiKhoan) {
            List<NguoiDung> users = nguoiDungRepository.findByTaiKhoanId(idTaiKhoan);
            return !users.isEmpty() ? users.get(0) : null; // Hoặc xử lý theo cách khác
        }



    }


