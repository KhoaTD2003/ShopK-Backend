//package com.example.demo.Services;
//
//import com.example.demo.Entities.ChiTietSanPham;
//import com.example.demo.Repositories.ChiTietSanPhamRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//@Service
//public class ChiTietSanPhamService {
//    @Autowired
//    private ChiTietSanPhamRepository ctspRepo;
//
//    public List<ChiTietSanPham> getAll() {
//        return ctspRepo.findAll();
//    }
//
//    public ChiTietSanPham add(ChiTietSanPham chiTietSanPham){
//        return ctspRepo.save(chiTietSanPham);
//    }
//
//    public ChiTietSanPham update(UUID id, ChiTietSanPham chiTietSanPham){
//        Optional<ChiTietSanPham> optionalChiTietSanPham = ctspRepo.findById(id);
//        if (optionalChiTietSanPham.isPresent()){
//            ChiTietSanPham ctsp = optionalChiTietSanPham.get();
//            ctsp.setGiaBan(chiTietSanPham.getGiaBan());
//            ctsp.setGiaNhap(chiTietSanPham.getGiaNhap());
//            ctsp.setSoLuongTon(chiTietSanPham.getSoLuongTon());
//            ctsp.setMoTa(chiTietSanPham.getMoTa());
//            ctsp.setTrangThai(chiTietSanPham.getTrangThai());
//            return  ctspRepo.save(chiTietSanPham);
//        } else {
//            throw new RuntimeException("Không tìm thấy với ID: " + id);
//        }
//
//    }
//
//
//    public void delete(UUID id) {
//        Optional<ChiTietSanPham> optionalChiTietSanPham = ctspRepo.findById(id);
//        if (optionalChiTietSanPham.isPresent()) {
//            ctspRepo.delete(optionalChiTietSanPham.get());
//        } else {
//            throw new RuntimeException("Không tìm với ID: " + id);
//        }
//    }
//}
