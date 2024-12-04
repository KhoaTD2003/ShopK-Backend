package com.example.demo.Controllers;


import com.example.demo.Entities.ChatLieu;
import com.example.demo.Entities.Size;
import com.example.demo.Services.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/chatlieu")
public class ChatLieuController {

    @Autowired
    private ChatLieuService chatLieuService;

    // Lấy tất cả chất liệu
    @GetMapping
    public List<ChatLieu> getAllChatLieu() {
        return chatLieuService.getAll();
    }

    @GetMapping("/page")
    public Page<ChatLieu> getAllThuongHieu(@RequestParam(defaultValue = "0") int pageNumber) {
        return chatLieuService.getAll(pageNumber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatLieu> getThuongHieuById(@PathVariable UUID id){
        ChatLieu xuatXu = chatLieuService.findById(id);
        return ResponseEntity.ok(xuatXu);
    }


    @PostMapping
    public ResponseEntity<?> add(@RequestBody ChatLieu xuatXu) {
        // Kiểm tra mã sản phẩm có tồn tại không
        if (chatLieuService.isMaterialCodeExist(xuatXu.getMa())) {
            return ResponseEntity.badRequest().body("Mã sản phẩm đã tồn tại");
        }
        if (chatLieuService.existsByName(xuatXu.getTen())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Tên thương hiệu đã tồn tại!"));
        }
        // Nếu mã thương hiệu chưa có, tự động sinh mã mới
        if (xuatXu.getMa() == null || xuatXu.getMa().isEmpty()) {
            String newBrandCode = chatLieuService.GenarateMaterialCode(); // Hàm tạo mã mới
            xuatXu.setMa(newBrandCode); // Gán mã mới vào thương hiệu
        }

        // Lưu thương hiệu vào cơ sở dữ liệu
        ChatLieu createdXuatXu= chatLieuService.add(xuatXu);

        return new ResponseEntity<>(createdXuatXu, HttpStatus.CREATED);
    }

    // Thêm mới chất liệu
//    @PostMapping
//    public ResponseEntity<ChatLieu> addChatLieu(@RequestBody ChatLieu chatLieu) {
//        ChatLieu newChatLieu = chatLieuService.add(chatLieu);
//        return ResponseEntity.ok(newChatLieu);
//    }

    // Cập nhật chất liệu theo id
    @PutMapping("/{id}")
    public ResponseEntity<ChatLieu> updateChatLieu(@PathVariable UUID id, @RequestBody ChatLieu chatLieuDetails) {
        ChatLieu updatedChatLieu = chatLieuService.update(id, chatLieuDetails);
        return ResponseEntity.ok(updatedChatLieu);
    }

    // Xóa chất liệu theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatLieu(@PathVariable UUID id) {
        chatLieuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

