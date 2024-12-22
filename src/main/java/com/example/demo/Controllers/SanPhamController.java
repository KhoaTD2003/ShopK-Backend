package com.example.demo.Controllers;

import com.example.demo.Dtos.SanPhamDto;
import com.example.demo.Entities.SanPham;
import com.example.demo.Services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sanpham")
public class SanPhamController {

    @Autowired
    private SanPhamService spService;

    @GetMapping()
    public Page<SanPhamDto> getAll(@RequestParam(value = "search", required = false) String search,
                                   @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                   @RequestParam(value = "thuonghieu", required = false) String thuongHieu,
                                   @RequestParam(value = "theloai", required = false) String theLoai,
                                   @RequestParam(value = "mausac", required = false) String mauSac,
                                   @RequestParam(value = "size", required = false) String size,
                                   @RequestParam(value = "minPrice", defaultValue = "0") double minPrice,
                                   @RequestParam(value = "maxPrice", defaultValue = "1.7976931348623157E308") double maxPrice,
                                   @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber


    ) {

        String sort = "desc";
        if (sortOrder != null) {
            sort = sortOrder;
        }
        // search
        if (search != null) {
            // Nếu có tên tìm kiếm (tenSP), tìm kiếm sản phẩm và sắp xếp theo giá nếu có sortOrder
            return spService.searchAndSortProducts(search, sort,pageNumber);
        }
        // ? brand=adidas => brand != null\
        if (thuongHieu != null) {
            return spService.findByThuongHieu(thuongHieu,pageNumber);
        }
        if(theLoai != null){
            return spService.findByTheLoai(theLoai,pageNumber);
        }
        if(mauSac != null){
            return spService.findByMauSac(mauSac,pageNumber);
        }
        if(size != null){
            return spService.findBySize(size,pageNumber);
        }if (minPrice > 0 || maxPrice < Double.MAX_VALUE) {
            return spService.findByPriceBetween(minPrice, maxPrice, pageNumber);
        }

        return spService.getAllProductDetailsSortedByPrice(sort,pageNumber);
    }

    @PostMapping()
    public ResponseEntity<SanPham> add(@RequestBody SanPham sanPham) {
        SanPham ctsp = spService.add(sanPham);
        return ResponseEntity.ok(ctsp);
    }


//    @GetMapping("/sorted-by-price")
//    public ResponseEntity<List<SanPhamDto>> getAllProductDetailsSortedByPrice(
//            @RequestParam(value = "sortOrder", required = false) String sortOrder) {
//
//        // Gọi service để lấy danh sách sản phẩm với thứ tự sắp xếp
//        List<SanPhamDto> products = spService.getAllProductDetailsSortedByPrice(sortOrder);
//
//        return ResponseEntity.ok(products);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPham> update(@PathVariable UUID id, @RequestBody SanPham sanPham) {
        SanPham sp = spService.update(id, sanPham);
        return ResponseEntity.ok(sp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        spService.delete(id);
        return ResponseEntity.noContent().build();
    }

//sp detail
    @GetMapping("/maSP")
    public ResponseEntity<SanPhamDto> getSanPhamByMaSP(@RequestParam("maSP") String maSP) {
        SanPhamDto sanPham = spService.findByMaSP(maSP);
        System.out.println(sanPham);
        if (sanPham != null) {
            return ResponseEntity.ok(sanPham);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
