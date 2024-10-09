package com.example.demo.Services;

import com.example.demo.Entities.NhanVien;
import com.example.demo.Repositories.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class NhanVienService {
    @Autowired
    private NhanVienRepository repository;
    public List<NhanVien> getAll(){
        return repository.findAll();

    }
    public NhanVien add(NhanVien nhanVien){
        return repository.save(nhanVien);
    }
    // update dữ liệu theo id
    public NhanVien update(UUID id, NhanVien nhanVienDetail){
        Optional<NhanVien> optionalNhanVien = repository.findById(id);
        if(optionalNhanVien.isPresent()){
            NhanVien nhanVien = optionalNhanVien.get();
            nhanVien.setMaNV(nhanVienDetail.getMaNV());
            nhanVien.setHoTen(nhanVienDetail.getHoTen());
            nhanVien.setNamSinh(new Date());
            nhanVien.setChucVu(nhanVienDetail.getChucVu());
            nhanVien.setDiaChi(nhanVienDetail.getDiaChi());
            nhanVien.setEmail(nhanVienDetail.getEmail());
            nhanVien.setSoDT(nhanVienDetail.getSoDT());
            nhanVien.setTrangThai(nhanVienDetail.getTrangThai());
            return repository.save(nhanVien);
        }else {
            throw new RuntimeException("không tìm thấy nhân viên ID "+id);
        }
    }
    // delete dữ liệu theo id
    public void delete(UUID id){
        Optional<NhanVien>optionalNhanVien = repository.findById(id);
        if(optionalNhanVien.isPresent()){
            repository.delete(optionalNhanVien.get());
        }else{
            throw new RuntimeException("không tìm thấy nhân viên ID "+id);
        }
    }
}
