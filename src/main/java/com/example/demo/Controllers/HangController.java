package com.example.demo.Controllers;

import com.example.demo.Entities.Hang;
import com.example.demo.Services.HangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hang")
public class HangController {

    @Autowired
    private HangService hangService;

    @GetMapping
    public List<Hang> getAll() {
        return this.hangService.getAll();
    }

    @PostMapping
    public ResponseEntity<Hang> addHang(@RequestBody Hang hang) {
        Hang newHang = this.hangService.add(hang);
        return ResponseEntity.ok(newHang);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hang> updateHang(@PathVariable UUID id, @RequestBody Hang hangDetail) {
        Hang updateHang = this.hangService.update(id, hangDetail);
        return ResponseEntity.ok(updateHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHang(@PathVariable UUID id) {
        this.hangService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
