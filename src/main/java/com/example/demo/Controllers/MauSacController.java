package com.example.demo.Controllers;

import com.example.demo.Entities.MauSac;
import com.example.demo.Services.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mausac")
public class MauSacController {
    @Autowired
    private MauSacService msService;


    @GetMapping
    public List<MauSac> getAll() {
        return msService.getAll();
    }

    @PostMapping
    public ResponseEntity<MauSac> add(@RequestBody MauSac mauSac) {
        MauSac ms = msService.add(mauSac);
        return ResponseEntity.ok(ms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MauSac> update(@PathVariable UUID id, @RequestBody MauSac mauSac) {
        MauSac ms = msService.update(id, mauSac);
        return ResponseEntity.ok(ms);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        msService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
