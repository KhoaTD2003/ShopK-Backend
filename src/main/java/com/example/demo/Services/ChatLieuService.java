package com.example.demo.Services;

import com.example.demo.Entities.ChatLieu;
import com.example.demo.Repositories.ChatLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ChatLieu add(ChatLieu chatLieu){
        return repository.save(chatLieu);
    }

    // Cập nhật chất liệu theo id
    public ChatLieu update(UUID id, ChatLieu chatLieuDetails){
        Optional<ChatLieu> optionalChatLieu = repository.findById(id);
        if(optionalChatLieu.isPresent()){
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
        if(optionalChatLieu.isPresent()){
            repository.delete(optionalChatLieu.get());
        } else {
            throw new RuntimeException("Không tìm thấy chất liệu với ID: " + id);
        }
    }
}
