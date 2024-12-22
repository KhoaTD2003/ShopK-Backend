package com.example.demo.Services;

import com.example.demo.Entities.*;
import com.example.demo.Repositories.ChatLieuRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatLieuService {

    @Autowired
    private ChatLieuRepository repository;

    public List<ChatLieu> getAll() {
        return repository.findAll();
    }

    public Page<ChatLieu> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12, Sort.by("ngayTao").descending());
        return repository.findAll(pageable);
    }

    public String GenarateMaterialCode() {
        // Lấy mã sản phẩm lớn nhất từ cơ sở dữ liệu
        Integer maxBrandNumber = repository.getMaxMaterialCode();

        // Nếu không có sản phẩm nào, bắt đầu từ SP001
        if (maxBrandNumber == null) {
            return "CL01";
        }

        // Tăng số lên 1 và tạo mã mới theo định dạng SPxxx
        int newBrandNumber = maxBrandNumber + 1;
        return String.format("CL%3d", newBrandNumber);
    }
    // Kiểm tra mã sản phẩm đã tồn tại hay chưa
    public boolean isMaterialCodeExist(String ma) {
        return repository.existsByMa(ma); // Kiểm tra mã sản phẩm có tồn tại trong cơ sở dữ liệu
    }

    public boolean existsByName(String name) {
        return repository.existsByTen(name); // Gọi repo để kiểm tra
    }
    public ChatLieu add(ChatLieu chatLieu) {
        return repository.save(chatLieu);
    }

    // Cập nhật chất liệu theo id
    public ChatLieu update(UUID id, ChatLieu chatLieuDetails) {
        Optional<ChatLieu> optionalChatLieu = repository.findById(id);
        if (optionalChatLieu.isPresent()) {
            ChatLieu chatLieu = optionalChatLieu.get();
            chatLieu.setTen(chatLieuDetails.getTen()); // Ví dụ: cập nhật tên
            chatLieu.setMa(chatLieuDetails.getMa());
            // Cập nhật các thuộc tính khác của ChatLieu nếu có
            return repository.save(chatLieu);
        } else {
            throw new RuntimeException("Không tìm thấy chất liệu với ID: " + id);
        }
    }

    // Xóa chất liệu theo id
    public void delete(UUID id) {
        Optional<ChatLieu> optionalChatLieu = repository.findById(id);
        if (optionalChatLieu.isPresent()) {
            repository.delete(optionalChatLieu.get());
        } else {
            throw new RuntimeException("Không tìm thấy chất liệu với ID: " + id);
        }
    }

    public ChatLieu findById(UUID id) {
        return repository.findById(id).orElse(null);
    }
}

//
//    @Id
//    @GeneratedValue(generator = "UUID")
//    @Column(name = "id")
//    private UUID id; // ID chi tiết hóa đơn
//
//    @ManyToOne // Mối quan hệ nhiều đến một với HoaDon
//    @JoinColumn(name = "id_hoadon", nullable = false)
//    private HoaDon hoaDon; // Tham chiếu đến hóa đơn
//
//    @ManyToOne // Mối quan hệ nhiều đến một với SanPham
//    @JoinColumn(name = "id_sanpham", nullable = false)
//    private SanPham sanPham; // Tham chiếu đến sản phẩm
//
//    @Column(name = "soluong", nullable = false)
//    private int soLuong; // Số lượng
//
//    @Column(name = "dongia", nullable = false)
//    private BigDecimal donGia; // Đơn giá
//
//    @Column(name = "tongtien", nullable = false)
//    private BigDecimal tongTien; // Tổng tiền
//
//    @Column(name = "trangthai", nullable = false)
//    private Boolean trangThai; // Tổng tiền
//
//    @Column(name = "ghichu", nullable = false)
//    private String ghiChu; // Tổng tiền


//    private String maHoaDon; // Mã hóa đơn
//    private String tenSanPham; // Tên sản phẩm
//    private int soLuong;
//    private BigDecimal donGia;
//    private BigDecimal tongTien;
//    private String ghiChu;
//    private boolean trangThai;



// Bước 3: Lưu thông tin chi tiết hóa đơn
//            for (ChiTietSanPham sanPham : request.getSanPhamList()) {
//                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
//                    chiTietHoaDon.setHoaDon(hoaDon); // Gán đối tượng hóa đơn
//                    // Tìm sản phẩm từ cơ sở dữ liệu dựa trên ID
//                    SanPham sanPhamDb = sanPhamService.getProductById(sanPham.getIdSanPham());
//                    if (sanPhamDb == null) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sản phẩm không tồn tại");
//                    }
//
//                    chiTietHoaDon.setSanPham(sanPhamDb); // Gán đối tượng sản phẩm
//
////                chiTietHoaDon.setIdHoaDon(hoaDon.getId()); // Gán ID hóa đơn
////                chiTietHoaDon.setIdSanPham(sanPham.getIdSanPham()); // Gán ID sản phẩm
//                    chiTietHoaDon.setSoLuong(sanPham.getSoLuong()); // Gán số lượng
//                    chiTietHoaDon.setDonGia(BigDecimal.valueOf(sanPham.getDonGia())); // Gán đơn giá
//                    chiTietHoaDon.setTongTien(BigDecimal.valueOf(sanPham.getSoLuong() * sanPham.getDonGia())); // Tính tổng tiền
