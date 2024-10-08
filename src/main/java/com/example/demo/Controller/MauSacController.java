package com.example.demo.Controller;

import com.example.demo.Entities.MauSac;
import com.example.demo.Services.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mausac")
public class MauSacController {
    @Autowired
    private MauSacService msService;


    @GetMapping("/hien-thi")
    public List<MauSac> getAll() {
        return msService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<MauSac> add(@RequestBody MauSac mauSac) {
        MauSac ms = msService.add(mauSac);
        return ResponseEntity.ok(ms);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MauSac> update(@PathVariable UUID id, @RequestBody MauSac mauSac) {
        MauSac ms = msService.update(id, mauSac);
        return ResponseEntity.ok(ms);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        msService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
