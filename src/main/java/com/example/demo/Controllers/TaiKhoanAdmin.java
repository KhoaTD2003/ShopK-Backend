package com.example.demo.Controllers;

import com.example.demo.Dtos.SanPhamAdminDto;
import com.example.demo.Dtos.SanPhamDto;
import com.example.demo.Entities.NguoiDung;
import com.example.demo.Entities.SanPham;
import com.example.demo.Entities.TaiKhoan;
import com.example.demo.Repositories.NguoiDungRepository;
import com.example.demo.Repositories.SanPhamRepository;
import com.example.demo.Services.NguoiDungService;
import com.example.demo.Services.SanPhamService;
import com.example.demo.Services.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
    @RequestMapping("/api/loginAuth")
public class TaiKhoanAdmin {

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private SanPhamService sanPhamService;
//tài khoản

    @PostMapping()
    public ResponseEntity<?> loginAdmin(@RequestParam String tenTaiKhoan, @RequestParam String matKhau) {
        try {
            TaiKhoan taiKhoan = taiKhoanService.loginAdmin(tenTaiKhoan, matKhau);
            return ResponseEntity.ok(taiKhoan);  // Trả về đối tượng TaiKhoan mà không có UUID
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }


//    @GetMapping("/customer")
//    public List<NguoiDung> getCustomer(@RequestParam(defaultValue = "khách hàng") String role) {
//        // Gọi service để lấy danh sách người dùng theo role
//        return nguoiDungService.getUsersByRole(role);
//    }

    //    @GetMapping("/employee")
//    public List<NguoiDung> getStaff(@RequestParam(defaultValue = "Nhân viên") String role) {
//        // Gọi service để lấy danh sách người dùng theo role
//        return nguoiDungService.getUsersByRole(role);
//    }
//    @GetMapping("/employee")
//    public Page<NguoiDung> getStaff(
//            @RequestParam(defaultValue = "Nhân viên" ,required = false) String role,
//            @RequestParam(value = "hoTen" ,required = false) String hoTen,
//            @RequestParam(value = "sdt" ,required = false) String sdt,
//            @RequestParam(value = "trangThai" ,required = false) Boolean trangThai,
//            @RequestParam(value = "pageNumber" ,defaultValue = "0") int pageNumber
//    ) {
//        // Kiểm tra từng điều kiện
//        if ((hoTen != null && !hoTen.isEmpty()) || (sdt != null && !sdt.isEmpty())) {
//            return nguoiDungService.getUsersByHoTenOrSdtAndRole(hoTen, sdt, role, pageNumber);
//        } else if (trangThai != null) {
//            return nguoiDungService.getUsersByTrangThaiAndRole(trangThai, role, pageNumber);
//        } else {
//            return nguoiDungService.getUsersByRole(role, pageNumber);
//        }
//
//    }

    @GetMapping("/employee")
    public Page<TaiKhoan> getStaff(
            @RequestParam(defaultValue = "Nhân viên" ,required = false) String role,
            @RequestParam(value = "tenTaiKhoan" ,required = false) String tenTaiKhoan,
            @RequestParam(value = "sdt" ,required = false) String sdt,
            @RequestParam(value = "trangThai" ,required = false) Boolean trangThai,
            @RequestParam(value = "pageNumber" ,defaultValue = "0") int pageNumber
    ) {

            return taiKhoanService.getAll(tenTaiKhoan,sdt,trangThai,role,pageNumber);


    }

    @GetMapping("/customer")
    public Page<TaiKhoan> getCustomer(
            @RequestParam(defaultValue = "Khách hàng" ,required = false) String role,
            @RequestParam(required = false) String tenTaiKhoan,
            @RequestParam(required = false) String sdt,
            @RequestParam(required = false) Boolean trangThai,
            @RequestParam(defaultValue = "0") int pageNumber
    ) {

        return taiKhoanService.getAll(tenTaiKhoan,sdt,trangThai,role,pageNumber);

//        // Kiểm tra từng điều kiện
//        if ((hoTen != null && !hoTen.isEmpty()) || (sdt != null && !sdt.isEmpty())) {
//            return nguoiDungService.getUsersByHoTenOrSdtAndRole(hoTen, sdt, role, pageNumber);
//        } else if (trangThai != null) {
//            return nguoiDungService.getUsersByTrangThaiAndRole(trangThai, role, pageNumber);
//        } else {
//            return nguoiDungService.getUsersByRole(role, pageNumber);
//        }

    }

    @PutMapping("/updateStatus/{userId}")
    public String updateStatus(@PathVariable("userId") UUID userId, @RequestParam("trangThai") boolean trangThai) {
        System.out.println("User ID: " + userId);
        System.out.println("Trang Thai: " + trangThai);
        boolean result = taiKhoanService.updateStatus(userId, trangThai);
        if (result) {
            return "Trạng thái người dùng đã được cập nhật thành công.";
        } else {
            return "Không tìm thấy người dùng với ID: " + userId;
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<TaiKhoan> updateUser(@PathVariable UUID id, @RequestBody TaiKhoan updatedUser) {
        TaiKhoan updated = taiKhoanService.update(id, updatedUser);
        if (updated != null) {
            return ResponseEntity.ok(updated);  // Trả về người dùng đã cập nhật
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Không tìm thấy người dùng
        }
    }

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<TaiKhoan> updateRole(@PathVariable UUID id, @RequestParam String role) {
        TaiKhoan updated = taiKhoanService.updateUserRole(id, role);
        if (updated != null) {
            return ResponseEntity.ok(updated);  // Trả về người dùng đã cập nhật
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Không tìm thấy người dùng
        }
    }

//    @GetMapping("/checkEmail")
//    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
//        boolean exists = nguoiDungRepository.findByEmail(email) != null;
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/checkSdt")
//    public ResponseEntity<Map<String, Boolean>> checkSdt(@RequestParam String sdt) {
//        boolean exists = nguoiDungRepository.findBySdt(sdt) != null;
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }


    @GetMapping("/getUser/{userId}")
    public ResponseEntity<TaiKhoan> getUserById(@PathVariable String userId) {
        try {
            // Gọi service để lấy người dùng
            TaiKhoan user = taiKhoanService.getUserById(UUID.fromString(userId));  // Chuyển đổi String thành UUID
            if (user != null) {
                return ResponseEntity.ok(user);  // Trả về thông tin người dùng
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Trả về 404 nếu không tìm thấy người dùng
            }
        } catch (IllegalArgumentException e) {
            // Trường hợp UUID không hợp lệ
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Xóa người dùng và tài khoản liên quan
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID id) {
        TaiKhoan userToDelete = taiKhoanService.getUserById(id);

        if (userToDelete != null) {
            // Gọi service để xóa người dùng và tài khoản liên quan
            boolean deleted = taiKhoanService.deleteTaiKhoan(userToDelete);
            if (deleted) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(500).body("Error deleting user");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
    //sản phẩm

//    @GetMapping("/product")
//    public ResponseEntity<Page<SanPhamAdminDto>> getAllProducts(@RequestParam(defaultValue = "0") int page) {
//        Page<SanPhamAdminDto> products = sanPhamService.getAllProducts(page);
//        return ResponseEntity.ok(products);
//    }

    @GetMapping("/product")
    public ResponseEntity<Page<SanPhamAdminDto>> getProducts(
            @RequestParam(value = "tenSP", required = false) String tenSP,
            @RequestParam(value = "soLuongTon", required = false) Integer soLuongTon,
            @RequestParam(value = "sortByPrice", required = false, defaultValue = "asc") String sortByPrice,
            @RequestParam(value = "trangThai", required = false) Boolean trangThai, // Thêm tham số trạng thái
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "12") int size) {
        try {
            Page<SanPhamAdminDto> products = sanPhamService.getProducts(tenSP, soLuongTon, sortByPrice, page, size, trangThai);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    public ResponseEntity<Page<SanPhamAdminDto>> getAllProducts(
//            @RequestParam(value = "tenSP", required = false, defaultValue = "") String tenSP,
//            @RequestParam(value = "page", defaultValue = "0") int page) {
//        try {
//            Page<SanPhamAdminDto> products;
//
//            // Kiểm tra nếu `tenSP` không rỗng, thực hiện tìm kiếm theo tên
//            if (tenSP != null && !tenSP.trim().isEmpty()) {
//                products = sanPhamService.getAllProducts2(tenSP, page);
//            } else {
//                // Ngược lại, trả về toàn bộ sản phẩm
//                products = sanPhamService.getAllProducts(page);
//            }
//
//            return ResponseEntity.ok(products); // Trả về kết quả
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Xử lý lỗi
//        }
//    }
//


    @PutMapping("/upStatusProduct/{productId}")
    public String updateStatusProduct(@PathVariable("productId") UUID productId, @RequestParam("trangThai") boolean trangThai) {
        System.out.println("productId ID: " + productId);
        System.out.println("Trang Thai: " + trangThai);
        boolean result = sanPhamService.updateStatusProduct(productId, trangThai);
        if (result) {
            return "Trạng thái người dùng đã được cập nhật thành công.";
        } else {
            return "Không tìm thấy người dùng với ID: " + productId;
        }
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") UUID id) {
        SanPham prDelete = sanPhamService.getProductById(id);

        if (prDelete != null) {
            // Gọi service để xóa người dùng và tài khoản liên quan
            boolean deleted = sanPhamService.deleteProduct(prDelete);
            if (deleted) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(500).body("Error deleting user");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }


}


