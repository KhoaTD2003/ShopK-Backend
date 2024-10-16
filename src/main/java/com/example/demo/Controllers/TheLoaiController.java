package com.example.demo.Controllers;

import com.example.demo.Entities.TheLoai;
import com.example.demo.Services.TheLoaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/theloai")
public class TheLoaiController {

    @Autowired
    private TheLoaiService theLoaiService;

    @GetMapping
    public List<TheLoai> getAll() {
        return this.theLoaiService.getAll();
    }

    //Phân trang
    @GetMapping("/paged")
    public ResponseEntity<Page<TheLoai>> getPagedTheLoai(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Page<TheLoai> result = theLoaiService.getAllPaged(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TheLoai> add(@RequestBody TheLoai theLoai) {
        TheLoai newTheLoai = this.theLoaiService.add(theLoai);
        return ResponseEntity.ok(newTheLoai);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TheLoai> update(@PathVariable UUID id, @RequestBody TheLoai theLoaiDetail) {
        TheLoai hang = this.theLoaiService.update(id, theLoaiDetail);
        return ResponseEntity.ok(hang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.theLoaiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
