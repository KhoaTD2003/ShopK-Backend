package com.example.demo.Services;


import com.example.demo.Dtos.NguoiDungDto;
import com.example.demo.Dtos.UserDto;
import com.example.demo.Entities.NguoiDung;
import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Repositories.NguoiDungRepository;
import com.example.demo.Repositories.TaiKhoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
    public class NguoiDungService {

        @Autowired
        private NguoiDungRepository nguoiDungRepository;

        @Autowired
        private TaiKhoanRepository taiKhoanRepository;
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
            return nguoiDungRepository.findNguoiDungDtoByIdTaiKhoan(idTaiKhoan);
        }


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

//admin page

        public List<NguoiDung> getUsersByRole(String role) {
            return nguoiDungRepository.findByTaiKhoan_Role(role);
        }


    public boolean updateStatus(UUID userId, boolean trangThai) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(userId).orElse(null);
        if (nguoiDung != null) {
            nguoiDung.setTrangThai(trangThai);
            nguoiDungRepository.save(nguoiDung);
            return true;
        } else {
            System.out.println("Không tìm thấy người dùng với ID: " + userId);
            return false;
        }
    }

    public NguoiDung getUserById(UUID userId) {
        return nguoiDungRepository.findById(userId).orElse(null);  // Tìm người dùng theo UUID
    }

    public NguoiDung updateUser(UUID id, NguoiDung updatedUser) {
        // Kiểm tra người dùng có tồn tại không
        Optional<NguoiDung> existingUserOpt = nguoiDungRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            NguoiDung existingUser = existingUserOpt.get();

            // Kiểm tra email có trùng không
//            if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(existingUser.getEmail())) {
//                NguoiDung userByEmail = nguoiDungRepository.findByEmail(updatedUser.getEmail());
//                if (userByEmail != null) {
//                    // Nếu email đã tồn tại
//                    throw new IllegalArgumentException("Email đã được sử dụng bởi người dùng khác");
//                }
//            }
//
//            // Kiểm tra SĐT có trùng không
//            if (updatedUser.getSdt() != null && !updatedUser.getSdt().equals(existingUser.getSdt())) {
//                NguoiDung userBySdt = nguoiDungRepository.findBySdt(updatedUser.getSdt());
//                if (userBySdt != null) {
//                    // Nếu SĐT đã tồn tại
//                    throw new IllegalArgumentException("Số điện thoại đã được sử dụng bởi người dùng khác");
//                }
//            }

            // Cập nhật các trường nếu không bị trùng
            if (updatedUser.getHoTen() != null) existingUser.setHoTen(updatedUser.getHoTen());
            if (updatedUser.getNamSinh() != null) existingUser.setNamSinh(updatedUser.getNamSinh());
            if (updatedUser.getDiaChi() != null) existingUser.setDiaChi(updatedUser.getDiaChi());
            if (updatedUser.getEmail() != null) existingUser.setEmail(updatedUser.getEmail());
            if (updatedUser.getSdt() != null) existingUser.setSdt(updatedUser.getSdt());

            if (updatedUser.getTaiKhoan() != null && updatedUser.getTaiKhoan().getRole() != null) {
                existingUser.getTaiKhoan().setRole(updatedUser.getTaiKhoan().getRole());
            }

            // Lưu người dùng đã cập nhật
            return nguoiDungRepository.save(existingUser);
        }
        return null; // Nếu không tìm thấy người dùng
    }


    public NguoiDung updateUserRole(UUID id, String role) {
        // Tìm người dùng theo ID
        Optional<NguoiDung> existingUserOpt = nguoiDungRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            NguoiDung existingUser = existingUserOpt.get();

            // Lấy tài khoản của người dùng
            TaiKhoan taiKhoan = existingUser.getTaiKhoan();  // Đây là tài khoản của người dùng
            if (taiKhoan != null) {
                // Cập nhật role trong tài khoản
                taiKhoan.setRole(role);  // Gán giá trị mới cho role
                // Lưu lại tài khoản đã cập nhật
                taiKhoanRepository.save(taiKhoan);
            }
            // Lưu lại người dùng (nếu có thay đổi nào khác ngoài role)
            return nguoiDungRepository.save(existingUser);
        }
        return null; // Trả về null nếu không tìm thấy người dùng
    }

    // Xóa người dùng và tài khoản liên quan
    public boolean deleteNguoiDung(NguoiDung nguoiDung) {
        try {
            // Xóa tài khoản nếu tồn tại
            TaiKhoan taiKhoan = nguoiDung.getTaiKhoan();
            if (taiKhoan != null) {
                taiKhoanRepository.delete(taiKhoan);
            }

            // Xóa người dùng
            nguoiDungRepository.delete(nguoiDung);
            return true;
        } catch (Exception e) {
            return false;  // Nếu có lỗi xảy ra trong quá trình xóa
        }
    }



}


