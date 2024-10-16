package com.example.demo.Controllers;

import com.example.demo.Entities.XuatXu;
import com.example.demo.Services.XuatXuService;
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
@RequestMapping("/api/xuatxu")
public class XuatXuController {

    @Autowired
    private XuatXuService xuatXuService;

    @GetMapping
    public List<XuatXu> getAll() {
        return this.xuatXuService.getAll();
    }

    //Phân trang
    @GetMapping("/paged")
    public ResponseEntity<Page<XuatXu>> getPagedXuatXu(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Page<XuatXu> result = xuatXuService.getAllPaged(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<XuatXu> add(@RequestBody XuatXu xuatXu) {
        XuatXu newXuatXu = this.xuatXuService.add(xuatXu);
        return ResponseEntity.ok(newXuatXu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<XuatXu> update(@PathVariable UUID id, @RequestBody XuatXu xuatXuDetail) {
        XuatXu xuatXu = this.xuatXuService.update(id, xuatXuDetail);
        return ResponseEntity.ok(xuatXu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.xuatXuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
