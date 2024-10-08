package com.example.demo.Controllers;


import com.example.demo.Entities.ChatLieu;
import com.example.demo.Services.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // Thêm mới chất liệu
    @PostMapping
    public ResponseEntity<ChatLieu> addChatLieu(@RequestBody ChatLieu chatLieu) {
        ChatLieu newChatLieu = chatLieuService.add(chatLieu);
        return ResponseEntity.ok(newChatLieu);
    }

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

