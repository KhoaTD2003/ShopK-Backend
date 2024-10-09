package com.example.demo.Services;

import com.example.demo.Entities.HoaDon;
import com.example.demo.Repositories.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Controller
public class HoaDonService {
    @Autowired
    private HoaDonRepository repository;
    public List<HoaDon> getAll() {
        return repository.findAll();
    }
    public HoaDon add(HoaDon hoaDon) {
        return repository.save(hoaDon);
    }
    // update dữ liệu theo id
    public HoaDon update(UUID id, HoaDon hoaDonDetail){
        Optional<HoaDon> optionalHoaDon = repository.findById(id);
        if(optionalHoaDon.isPresent()){
            HoaDon hoaDon = optionalHoaDon.get();
            hoaDon.setMaHoaDon(hoaDonDetail.getMaHoaDon());
            hoaDon.setTenKH(hoaDonDetail.getTenKH());
            hoaDon.setTongTien(hoaDonDetail.getTongTien());
            hoaDon.setTienThu(hoaDonDetail.getTienThu());
            hoaDon.setTienGiam(hoaDonDetail.getTienGiam());
            hoaDon.setNgayTao(new Date());
            hoaDon.setTrangThai(hoaDonDetail.getTrangThai());
            hoaDon.setGhiChu(hoaDonDetail.getGhiChu());
            return repository.save(hoaDon);
        }else {
            throw new RuntimeException("không tìm thấy Hóa Đơn ID "+id);
        }
    }
    // delete dữ liệu theo id
    public void delete(UUID id){
        Optional<HoaDon>optionalHoaDon = repository.findById(id);
        if(optionalHoaDon.isPresent()){
            repository.delete(optionalHoaDon.get());
        }else{
            throw new RuntimeException("không tìm thấy hóa đơn ID "+id);
        }
    }
}
