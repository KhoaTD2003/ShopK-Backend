package com.example.demo.Services;

import com.example.demo.Dtos.NguoiDungDto;
import com.example.demo.Dtos.TaiKhoanDto;
import com.example.demo.Entities.ChatLieu;
import com.example.demo.Entities.NguoiDung;
import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Repositories.ChatLieuRepository;
import com.example.demo.Repositories.NguoiDungRepository;
import com.example.demo.Repositories.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TaiKhoanService {

    @Autowired
    private TaiKhoanRepository repository;

    @Autowired
    private NguoiDungService nguoiDungService;


    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private JavaMailSender mailSender;
//
//    @Autowired
//    private PasswordResetTokenRepository passwordResetTokenRepository;

    public List<TaiKhoan> getAll() {
        return repository.findAll();
    }

    public TaiKhoan add(TaiKhoan taiKhoan) {
        return repository.save(taiKhoan);
    }

    public TaiKhoan findById(UUID id) {
        Optional<TaiKhoan> optionalTaiKhoan = repository.findById(id);
        if (optionalTaiKhoan.isPresent()) {
            return optionalTaiKhoan.get();
        } else {
            throw new RuntimeException("Không tìm thấy tài khoản với ID: " + id);
        }
    }

    // Cập nhật chất liệu theo id
    public TaiKhoan update(UUID id, TaiKhoan taiKhoanDetails) {
        Optional<TaiKhoan> optionalTaiKhoan = repository.findById(id);
        if (optionalTaiKhoan.isPresent()) {
            TaiKhoan taiKhoan = optionalTaiKhoan.get();
            taiKhoan.setTenTaiKhoan(taiKhoanDetails.getTenTaiKhoan()); // Ví dụ: cập nhật tên
            taiKhoan.setMatKhau(taiKhoanDetails.getMatKhau());
            taiKhoan.setRole(taiKhoanDetails.getRole());
            taiKhoan.setTrangThai(taiKhoanDetails.isTrangThai());

            // Cập nhật các thuộc tính khác của ChatLieu nếu có
            return repository.save(taiKhoan);
        } else {
            throw new RuntimeException("Không tìm thấy tài khoản với ID: " + id);
        }
    }

    // Xóa chất liệu theo id
    public void deleteTaiKhoan(UUID taiKhoanId) {
        TaiKhoan taiKhoan = repository.findById(taiKhoanId).orElse(null);
        if (taiKhoan != null) {
            repository.delete(taiKhoan);  // Xóa tài khoản
        }
    }

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public boolean isEmailValid(String email) {
        return pattern.matcher(email).matches();
    }

    public boolean isEmailExists(String email) {
        return repository.findByEmail(email) != null;
    }

    public boolean isSdtExists(String sdt) {
        return repository.findBySdt(sdt) != null;
    }

    public boolean isTenTaiKhoanExists(String tenTaiKhoan) {
        return repository.findByTenTaiKhoan(tenTaiKhoan) != null;
    }

//    public String generateMaNguoiDung() {
//        return "USER-" + UUID.randomUUID().toString(); // Mã người dùng sẽ có dạng USER-xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
//    }

    //    public String generateMaNguoiDung() {
//        return "USER-" + Instant.now().toEpochMilli(); // USER-1677721623456
//    }
    public String generateMaNguoiDung() {
        return "USER-" + UUID.randomUUID().toString().substring(0, 4); // USER-xxxxxxxx
    }

    // Đăng ký tài khoản
    public TaiKhoanDto register(TaiKhoanDto taiKhoanDto) {
        if (!isEmailValid(taiKhoanDto.getEmail())) {
            throw new IllegalArgumentException("Địa chỉ email không hợp lệ!");
        }
        // Kiểm tra xem tên tài khoản đã tồn tại chưa
        if (repository.findByTenTaiKhoan(taiKhoanDto.getTenTaiKhoan()) != null) {
            throw new IllegalArgumentException("Tên tài khoản đã tồn tại.");
        }

        // Kiểm tra xem email đã tồn tại chưa
        if (repository.findByEmail(taiKhoanDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email đã được sử dụng.");
        }

        // Mã hóa mật khẩu trước khi lưu
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTenTaiKhoan(taiKhoanDto.getTenTaiKhoan());
        taiKhoan.setMatKhau(taiKhoanDto.getMatKhau());
        taiKhoan.setSdt(taiKhoanDto.getSdt());
        taiKhoan.setEmail(taiKhoanDto.getEmail());

        taiKhoan.setRole("Khách Hàng");  // hoặc "Khách hàng" nếu bạn sử dụng tiếng Việt
        taiKhoan.setTrangThai(true); // Đảm bảo tài khoản luôn được tạo với trạng thái "hoạt động"

        // Lưu tài khoản mới vào cơ sở dữ liệu
        TaiKhoan savedTaiKhoan = repository.save(taiKhoan);

        NguoiDungDto nguoiDungDto = new NguoiDungDto();
        nguoiDungDto.setMaNguoiDung(generateMaNguoiDung()); // Phương thức để tạo mã người dùng
        nguoiDungDto.setHoTen(taiKhoanDto.getTenTaiKhoan()); // Giả sử bạn đã thêm trường hoTen vào TaiKhoanDto
        nguoiDungDto.setEmail(taiKhoanDto.getEmail());
        nguoiDungDto.setSdt(taiKhoanDto.getSdt());
        nguoiDungDto.setIdTaiKhoan(savedTaiKhoan.getId()); // Lưu ID tài khoản vào người dùng
        nguoiDungDto.setTrangThai(true); // Đảm bảo trạng thái người dùng là "hoạt động"
        System.out.println("ID của tài khoản đã lưu: " + savedTaiKhoan.getId());
        nguoiDungService.saveOrUpdateNguoiDung(nguoiDungDto);

        // Trả về DTO sau khi đăng ký thành công
        return new TaiKhoanDto(
                savedTaiKhoan.getTenTaiKhoan(),
                savedTaiKhoan.getMatKhau(),
                savedTaiKhoan.getSdt(),
                savedTaiKhoan.getEmail(),
                savedTaiKhoan.getRole()
        );
    }

    public TaiKhoan login(String tenTaiKhoan, String matKhau) {
        // Tìm tài khoản trong cơ sở dữ liệu
        TaiKhoan taiKhoan = repository.findByTenTaiKhoan(tenTaiKhoan);

        // Kiểm tra nếu tài khoản không tồn tại
        if (taiKhoan == null) {
            throw new IllegalArgumentException("Tên tài khoản hoặc mật khẩu không chính xác.");
        }

        // Kiểm tra trạng thái tài khoản (nếu bị tắt thì không cho phép đăng nhập)
        if (!taiKhoan.isTrangThai()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tài khoản của bạn đã bị tắt.");
        }

        // Kiểm tra nếu vai trò là Admin thì không cho phép đăng nhập
        if ("Admin".equalsIgnoreCase(taiKhoan.getRole())) {
            throw new IllegalArgumentException("Tài khoản Admin không được phép đăng nhập.");
        }

        // Kiểm tra mật khẩu
        if (!taiKhoan.getMatKhau().equals(matKhau)) {
            throw new IllegalArgumentException("Tên tài khoản hoặc mật khẩu không chính xác.");
        }



        // Kiểm tra vai trò người dùng
        if ("Nhân Viên".equalsIgnoreCase(taiKhoan.getRole())) {
            // Nếu là nhân viên, trả về đối tượng TaiKhoan đã kiểm tra vai trò
            taiKhoan.setMatKhau(""); // Xóa mật khẩu trước khi trả về
            return taiKhoan; // Trả về thông tin nhân viên
        } else if ("Khách Hàng".equalsIgnoreCase(taiKhoan.getRole())) {
            // Nếu là khách hàng, trả về đối tượng TaiKhoan
            taiKhoan.setMatKhau(""); // Xóa mật khẩu trước khi trả về
            return taiKhoan; // Trả về thông tin khách hàng
        } else {
            // Nếu vai trò không hợp lệ
            throw new IllegalArgumentException("Vai trò người dùng không hợp lệ.");
        }
    }

//    public TaiKhoan login(String tenTaiKhoan, String matKhau) {
//        // Tìm tài khoản trong cơ sở dữ liệu
//        TaiKhoan taiKhoan = repository.findByTenTaiKhoan(tenTaiKhoan);
//        if (taiKhoan != null && taiKhoan.getMatKhau().equals(matKhau)) {
//            // Kiểm tra xem tài khoản có bị tắt không
//            if (!taiKhoan.isTrangThai()==false ) {
//                // Nếu tài khoản bị tắt, trả về lỗi 403 với thông báo
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tài khoản của bạn đã bị tắt");
//            }
//        }
//
//
//        if (taiKhoan != null && taiKhoan.getMatKhau().equals(matKhau)) {
//            // Kiểm tra nếu vai trò là Admin thì không cho phép đăng nhập
//            if ("admin".equalsIgnoreCase(taiKhoan.getRole())) {
//                throw new IllegalArgumentException("Tài khoản Admin không được phép đăng nhập.");
//            }
//
//            // Kiểm tra vai trò của người dùng
//            if ("Nhân Viên".equalsIgnoreCase(taiKhoan.getRole())) {
//                // Nếu là nhân viên, trả về đối tượng TaiKhoan đã kiểm tra vai trò
//                taiKhoan.setMatKhau(""); // Xóa mật khẩu trước khi trả về
//                return taiKhoan; // Trả về nhân viên
//            } else if ("Khách Hàng".equalsIgnoreCase(taiKhoan.getRole())) {
//                // Nếu là khách hàng, trả về đối tượng TaiKhoan
//                taiKhoan.setMatKhau(""); // Xóa mật khẩu trước khi trả về
//                return taiKhoan; // Trả về khách hàng
//            } else {
//                // Nếu vai trò không hợp lệ
//                throw new IllegalArgumentException("Vai trò người dùng không hợp lệ.");
//            }
//        } else {
//            // Trường hợp tài khoản không tồn tại hoặc mật khẩu sai
//            throw new IllegalArgumentException("Tên tài khoản hoặc mật khẩu không chính xác.");
//        }
//
//    }

    public void sendPasswordResetEmail(String email) {
        try {
            TaiKhoan taiKhoan = repository.findByEmail(email);
            if (taiKhoan != null) {
                String resetToken = UUID.randomUUID().toString(); // Tạo token ngẫu nhiên
                taiKhoan.setResetToken(resetToken);
                repository.save(taiKhoan); // Lưu lại token vào database

                String resetLink = "http://127.0.0.1:8080/api/reset-password.html?token=" + resetToken;

                // Gửi email
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject("Đặt lại mật khẩu");
                message.setText("Nhấn vào liên kết sau để đặt lại mật khẩu của bạn: " + resetLink);
                mailSender.send(message);
            }
        } catch (Exception e) {
            // Ghi log lỗi để biết nguyên nhân
            System.err.println("Lỗi gửi email: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public TaiKhoan resetPassword(String token, String newPassword) {
        TaiKhoan taiKhoan = repository.findByResetToken(token);
        if (taiKhoan != null) {
            taiKhoan.setMatKhau(newPassword); // Lưu mật khẩu mới
            taiKhoan.setResetToken(null); // Xóa token
            return repository.save(taiKhoan);
        }
        throw new RuntimeException("Token không hợp lệ hoặc đã hết hạn.");
    }

    //admin
    //login admin

    public TaiKhoan loginAdmin(String tenTaiKhoan, String matKhau) {
        TaiKhoan taiKhoan = repository.findByTenTaiKhoan(tenTaiKhoan);

        // Kiểm tra tài khoản có tồn tại và mật khẩu đúng
        if (taiKhoan != null && taiKhoan.getMatKhau().equals(matKhau)) {
            // Kiểm tra vai trò của tài khoản
            if ("Nhân Viên".equalsIgnoreCase(taiKhoan.getRole()) || "admin".equalsIgnoreCase(taiKhoan.getRole())) {
                // Xóa mật khẩu trước khi trả về
                taiKhoan.setMatKhau("");
                System.out.println("Mật khẩu đã bị xóa: " + taiKhoan.getMatKhau());

                return taiKhoan;  // Trả về đối tượng TaiKhoan
            } else {
                throw new IllegalArgumentException("Bạn không có quyền truy cập.");
            }
        } else {
            throw new IllegalArgumentException("Tên tài khoản hoặc mật khẩu không chính xác");
        }
    }


//    public List<TaiKhoanUsersDto> getAccountsByRole(String role) {
//        List<TaiKhoan> taiKhoanList = repository.findByRole(role);
//        return taiKhoanList.stream()
//                .map(this::mapToTaiKhoanNguoiDungDto)
//                .collect(Collectors.toList());
//    }

//    private TaiKhoanUsersDto mapToTaiKhoanNguoiDungDto(TaiKhoan taiKhoan) {
//        NguoiDung nguoiDung = nguoiDungRepository.findByIdTaiKhoan(taiKhoan.getId());
//
//        TaiKhoanUsersDto dto = new TaiKhoanUsersDto();
//        dto.setTaiKhoanId(taiKhoan.getId());
//        dto.setTenTaiKhoan(taiKhoan.getTenTaiKhoan());
//        dto.setEmailTaiKhoan(taiKhoan.getEmail());
//        dto.setSdtTaiKhoan(taiKhoan.getSdt());
//        dto.setRole(taiKhoan.getRole());
//        dto.setTrangThaiTaiKhoan(taiKhoan.isTrangThai());
//
//        if (nguoiDung != null) {
//            dto.setMaNguoiDung(nguoiDung.getMaNguoiDung());
//            dto.setHoTenNguoiDung(nguoiDung.getHoTen());
//            dto.setNamSinhNguoiDung(nguoiDung.getNamSinh());
//            dto.setDiaChiNguoiDung(nguoiDung.getDiaChi());
//            dto.setEmailNguoiDung(nguoiDung.getEmail());
//            dto.setSdtNguoiDung(nguoiDung.getSdt());
//            dto.setTrangThaiNguoiDung(nguoiDung.getTrangThai() != null ? nguoiDung.getTrangThai() : true);
//        }
//
//        return dto;
//    }

//    public TaiKhoan getTaiKhoanById(UUID id) {
//        return repository.findById(id).orElse(null);
//    }
//
//    public void updateTaiKhoanStatus(UUID id, boolean status) {
//        TaiKhoan taiKhoan = repository.findById(id).orElse(null);
//        if (taiKhoan != null) {
//            taiKhoan.setTrangThai(status);
//            repository.save(taiKhoan);
//        }
//    }

    public TaiKhoan save(TaiKhoan taiKhoan) {
        return repository.save(taiKhoan);
    }

    //qly tai khoan admin
    public Page<TaiKhoan> getAll(String tenTaiKhoan, String sdt, Boolean trangThai, String role, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12, Sort.by("ngayTao").descending());
        if (tenTaiKhoan != null && !tenTaiKhoan.isEmpty() || sdt != null && !sdt.isEmpty()) {
            // Tìm theo tên hoặc số điện thoại
            return repository.findTenTaiKhoanContainingOrSdtContainingByRole(tenTaiKhoan, sdt, role, pageable);
        } else if (trangThai != null) {
            // Tìm theo trạng thái
            return repository.findByTrangThaiAndRole(trangThai, role, pageable);
        } else {
            // Lấy tất cả tài khoản
            return repository.findByRole(role,pageable);
        }
    }

    public boolean updateStatus(UUID userId, boolean trangThai) {
        TaiKhoan taiKhoan = repository.findById(userId).orElse(null);
        if (taiKhoan != null) {
            taiKhoan.setTrangThai(trangThai);
            repository.save(taiKhoan);
            return true;
        } else {
            System.out.println("Không tìm thấy người dùng với ID: " + userId);
            return false;
        }
    }

    public TaiKhoan getUserById(UUID userId) {
        return repository.findById(userId).orElse(null);  // Tìm người dùng theo UUID
    }

    public boolean deleteTaiKhoan(TaiKhoan taiKhoan) {
        try {
            // Xóa tài khoản nếu tồn tại
            NguoiDung nguoiDung = taiKhoan.getNguoiDung();
            if (nguoiDung != null) {
                nguoiDungRepository.delete(nguoiDung);
            }

            // Xóa người dùng
            repository.delete(taiKhoan);
            return true;
        } catch (Exception e) {
            return false;  // Nếu có lỗi xảy ra trong quá trình xóa
        }
    }

    public TaiKhoan updateUserRole(UUID id, String role) {
        TaiKhoan taiKhoan = repository.findById(id).orElse(null);
        if (taiKhoan != null) {
            taiKhoan.setRole(role);
            return repository.save(taiKhoan);

        } else {
            System.out.println("Không tìm thấy người dùng với ID: " + id);
            return null;
        }
    }
}