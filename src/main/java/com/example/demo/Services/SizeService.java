package com.example.demo.Services;

import com.example.demo.Entities.Size;
import com.example.demo.Repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class SizeService {
    @Autowired
    private SizeRepository sRepo;

    public List<Size> getAll() {
        return sRepo.findAll();
    }

    public Size add(Size size){
        return sRepo.save(size);
    }

    public Size update(UUID id, Size size){
        Optional<Size> optionalSize = sRepo.findById(id);
        if(optionalSize.isPresent()){
            Size s = optionalSize.get();
            s.setMa(size.getMa());
            s.setTen(size.getTen());

            return sRepo.save(size);
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<Size> optionalSize = sRepo.findById(id);
        if(optionalSize.isPresent()){
            sRepo.delete(optionalSize.get());
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }


}
