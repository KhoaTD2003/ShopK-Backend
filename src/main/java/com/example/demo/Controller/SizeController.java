package com.example.demo.Controller;

import com.example.demo.Entities.Size;
import com.example.demo.Services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/size")
public class SizeController {
    @Autowired
    private SizeService sService;


    @GetMapping("/hien-thi")
    public List<Size> getAll() {
        return sService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Size> add(@RequestBody Size size) {
        Size s = sService.add(size);
        return ResponseEntity.ok(s);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Size> update(@PathVariable UUID id, @RequestBody Size size) {
        Size s = sService.update(id, size);
        return ResponseEntity.ok(s);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
