package com.example.demo.Controllers;

import com.example.demo.Entities.Size;
import com.example.demo.Services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/size")
public class SizeController {
    @Autowired
    private SizeService sService;


    @GetMapping()
    public List<Size> getAll() {
        return sService.getAll();
    }

    @PostMapping()
    public ResponseEntity<Size> add(@RequestBody Size size) {
        Size s = sService.add(size);
        return ResponseEntity.ok(s);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Size> update(@PathVariable UUID id, @RequestBody Size size) {
        Size s = sService.update(id, size);
        return ResponseEntity.ok(s);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public Page<Size> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size) {
            return sService.getAll(page, size);
    }


}
