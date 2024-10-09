package com.example.demo.Services;


import com.example.demo.Entities.KhachHang;
import com.example.demo.Repositories.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class KhachHangService {
    @Autowired
    private KhachHangRepository repository;
    public List<KhachHang> getAll(){
        return repository.findAll();
    }
    public KhachHang add(KhachHang khachHang){
        return repository.save(khachHang);
    }
    public KhachHang update(UUID id , KhachHang khachHangDetil){
        Optional<KhachHang> optionalKhachHang = repository.findById(id);
        if(optionalKhachHang.isPresent()){
            KhachHang khachHang = optionalKhachHang.get();
            khachHang.setMaKH(khachHangDetil.getMaKH());
            khachHang.setHoTen(khachHangDetil.getHoTen());
            khachHang.setNamSinh(new Date());
            khachHang.setSoDT(khachHangDetil.getSoDT());
            khachHang.setEmail(khachHangDetil.getEmail());
            khachHang.setTrangThai(khachHangDetil.getTrangThai());
            return repository.save(khachHang);

        }else{
            throw  new RuntimeException("không tìm thấy khách hàng id "+id);
        }
    }
    public void delete(UUID id){
        Optional<KhachHang>optionalKhachHang = repository.findById(id);
        if(optionalKhachHang.isPresent()){
            repository.delete(optionalKhachHang.get());
        }else{
            throw  new RuntimeException("không tìm thấy khách hàng id"+id);
        }
    }
}
