package com.example.demo.Services;

import com.example.demo.Entities.MauSac;
import com.example.demo.Repositories.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MauSacService {
    @Autowired
    private MauSacRepository msRepo;

    public List<MauSac> getAll() {
        return msRepo.findAll();
    }

    public MauSac add(MauSac mauSac){
        return msRepo.save(mauSac);
    }

    public MauSac update(UUID id, MauSac mauSac){
        Optional<MauSac> optionalMauSac = msRepo.findById(id);
        if(optionalMauSac.isPresent()){
            MauSac ms = optionalMauSac.get();
            ms.setMa(mauSac.getMa());
            ms.setTen(mauSac.getTen());

            return msRepo.save(mauSac);
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }

    public void delete(UUID id) {
        Optional<MauSac> optionalMauSac = msRepo.findById(id);
        if (optionalMauSac.isPresent()) {
            msRepo.delete(optionalMauSac.get());
        } else {
            throw new RuntimeException("Không tìm thấy với ID: " + id);
        }
    }

}
