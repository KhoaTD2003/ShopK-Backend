package com.example.demo.Controllers;

import com.example.demo.Entities.*;
import com.example.demo.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductsController {


    @Autowired
    private ThuongHieuService brandService;

    @Autowired
    private MauSacService colorService;

    @Autowired
    private TheLoaiService categoryService;

    @Autowired
    private XuatXuService originService;

    @Autowired
    private ChatLieuService materialService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private SanPhamService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestParam("maSP") String maSP,
                                        @RequestParam("tenSP") String tenSP,
                                        @RequestParam("thuongHieu") UUID thuongHieuId,
                                        @RequestParam("mauSac") UUID mauSacId,
                                        @RequestParam("chatLieu") UUID chatLieuId,
                                        @RequestParam("xuatXu") UUID xuatXuId,
                                        @RequestParam("theLoai") UUID theLoaiId,
                                        @RequestParam("size") UUID sizeId,
                                        @RequestParam("giaBan") BigDecimal giaBan,
                                        @RequestParam("ton") Integer ton,
                                        @RequestParam("moTa") String moTa,
                                        @RequestParam("anh") MultipartFile anh,
                                        @RequestParam("trangThai") Boolean trangThai) {

        // Tạo danh sách để lưu lỗi
        Map<String, String> errors = new HashMap<>();

        // Kiểm tra từng trường
//        if (maSP == null || maSP.isEmpty()) errors.put("maSP", "Mã sản phẩm không được để trống");
        if (tenSP == null || tenSP.isEmpty()) errors.put("tenSP", "Tên sản phẩm không được để trống");
        if (thuongHieuId == null) errors.put("thuongHieu", "Thương hiệu không được để trống");
        if (mauSacId == null) errors.put("mauSac", "Màu sắc không được để trống");
        if (chatLieuId == null) errors.put("chatLieu", "Chất liệu không được để trống");
        if (xuatXuId == null) errors.put("xuatXu", "Xuất xứ không được để trống");
        if (theLoaiId == null) errors.put("theLoai", "Thể loại không được để trống");
        if (sizeId == null) errors.put("size", "Size không được để trống");
        if (giaBan == null || giaBan.compareTo(BigDecimal.ZERO) < 0)
            errors.put("giaBan", "Giá bán phải lớn hơn hoặc bằng 0");
        if (ton == null || ton < 0) errors.put("ton", "Số lượng tồn phải lớn hơn hoặc bằng 0");

        // Kiểm tra mã sản phẩm có tồn tại không
        if (productService.isProductCodeExist(maSP)) {
            return ResponseEntity.badRequest().body("Mã sản phẩm đã tồn tại");
        }

        // Lấy mã sản phẩm mới (nếu không có mã nào thì tự động tạo SP001)
        String newProductCode = productService.getMaxProductCode();

        // Trả về lỗi nếu có
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        // Xử lý ảnh (nếu có)
        String imageUrl = uploadImage(anh);

        // Tạo đối tượng SanPham từ dữ liệu nhận được
        SanPham product = new SanPham();
        product.setMaSP(newProductCode);
        product.setTenSP(tenSP);
        product.setGiaBan(giaBan);
        product.setSoLuongTon(ton);
        product.setMoTa(moTa);
        product.setAnh(imageUrl); // Lưu đường dẫn ảnh
        product.setTrangThai(trangThai);

        // Thiết lập các thuộc tính khóa ngoại từ ID (bạn cần truy vấn các đối tượng từ database)
        product.setThuongHieu(brandService.getThuongHieuById(thuongHieuId));  // Lấy thương hiệu theo ID
        product.setMauSac(colorService.findById(mauSacId));  // Lấy màu sắc theo ID
        product.setChatLieu(materialService.findById(chatLieuId));  // Lấy chất liệu theo ID
        product.setXuatXu(originService.findById(xuatXuId));  // Lấy xuất xứ theo ID
        product.setTheLoai(categoryService.findById(theLoaiId));  // Lấy thể loại theo ID
        product.setSize(sizeService.findById(sizeId));  // Lấy size theo ID

        // Lưu sản phẩm vào database
        SanPham savedProduct = productService.add(product);

        // Trả về kết quả
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }


    // Phương thức upload ảnh (ví dụ)
    private String uploadImage(MultipartFile file) {
        // Upload ảnh và trả về đường dẫn (path) lưu trữ ảnh
        String imagePath = "img/product/" + file.getOriginalFilename();
        // Xử lý lưu file vào thư mục cụ thể
        return imagePath;
    }


    @GetMapping("/attributes")
    public ResponseEntity<Object> getProductAttributes(
            @RequestParam(value = "brands", required = false, defaultValue = "false") boolean getBrands,
            @RequestParam(value = "colors", required = false, defaultValue = "false") boolean getColors,
            @RequestParam(value = "materials", required = false, defaultValue = "false") boolean getMaterials,
            @RequestParam(value = "origins", required = false, defaultValue = "false") boolean getOrigins,
            @RequestParam(value = "categories", required = false, defaultValue = "false") boolean getCategories,
            @RequestParam(value = "sizes", required = false, defaultValue = "false") boolean getSizes) {

        // Tạo một object chứa kết quả
        Map<String, Object> response = new HashMap<>();

        // Kiểm tra và thêm dữ liệu vào response nếu các tham số yêu cầu
        if (getBrands) {
            response.put("brands", brandService.getAllThuongHieu());
        }
        if (getColors) {
            response.put("colors", colorService.getAll());
        }
        if (getMaterials) {
            response.put("materials", materialService.getAll());
        }
        if (getOrigins) {
            response.put("origins", originService.getAll());
        }
        if (getCategories) {
            response.put("categories", categoryService.getAll());
        }
        if (getSizes) {
            response.put("sizes", sizeService.getAll());
        }

        // Trả về danh sách thuộc tính cần thiết
        return ResponseEntity.ok(response);
    }


    //        // Lấy danh sách thương hiệu
//        @GetMapping("/brands")
//        public ResponseEntity<List<ThuongHieu>> getBrands() {
//            List<ThuongHieu> brands = brandService.getAllThuongHieu();
//            return ResponseEntity.ok(brands);
//        }
//
//        // Lấy danh sách màu sắc
//        @GetMapping("/colors")
//        public ResponseEntity<List<MauSac>> getColors() {
//            List<MauSac> colors = colorService.getAll();
//            return ResponseEntity.ok(colors);
//        }
//
//        // Lấy danh sách chất liệu
//        @GetMapping("/materials")
//        public ResponseEntity<List<ChatLieu>> getMaterials() {
//            List<ChatLieu> materials = materialService.getAll();
//            return ResponseEntity.ok(materials);
//        }
//
//        // Lấy danh sách xuất xứ
//        @GetMapping("/origins")
//        public ResponseEntity<List<XuatXu>> getOrigins() {
//            List<XuatXu> origins = originService.getAll();
//            return ResponseEntity.ok(origins);
//        }
//
//        // Lấy danh sách thể loại
//        @GetMapping("/categories")
//        public ResponseEntity<List<TheLoai>> getCategories() {
//            List<TheLoai> categories = categoryService.getAll();
//            return ResponseEntity.ok(categories);
//        }
//
//        // Lấy danh sách size
//        @GetMapping("/sizes")
//        public ResponseEntity<List<Size>> getSizes() {
//            List<Size> sizes = sizeService.getAll();
//            return ResponseEntity.ok(sizes);
//        }
    @GetMapping("/{id}")
    public ResponseEntity<SanPham> getSanPhamById(@PathVariable UUID id) {
        try {
            SanPham sanPham = productService.getProductById(id);
            return ResponseEntity.ok(sanPham);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy sản phẩm
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") UUID id,
//                                           @RequestParam("maSP") String maSP,
                                           @RequestParam("tenSP") String tenSP,
                                           @RequestParam("thuongHieu") UUID thuongHieuId,
                                           @RequestParam("mauSac") UUID mauSacId,
                                           @RequestParam("chatLieu") UUID chatLieuId,
                                           @RequestParam("xuatXu") UUID xuatXuId,
                                           @RequestParam("theLoai") UUID theLoaiId,
                                           @RequestParam("size") UUID sizeId,
                                           @RequestParam("giaBan") BigDecimal giaBan,
                                           @RequestParam("ton") Integer ton,
                                           @RequestParam("moTa") String moTa,
                                           @RequestParam("anh") MultipartFile anh,
                                           @RequestParam("trangThai") Boolean trangThai) {

        // Tạo danh sách để lưu lỗi
        Map<String, String> errors = new HashMap<>();

        // Kiểm tra từng trường
        if (tenSP == null || tenSP.isEmpty()) errors.put("tenSP", "Tên sản phẩm không được để trống");
        if (thuongHieuId == null) errors.put("thuongHieu", "Thương hiệu không được để trống");
        if (mauSacId == null) errors.put("mauSac", "Màu sắc không được để trống");
        if (chatLieuId == null) errors.put("chatLieu", "Chất liệu không được để trống");
        if (xuatXuId == null) errors.put("xuatXu", "Xuất xứ không được để trống");
        if (theLoaiId == null) errors.put("theLoai", "Thể loại không được để trống");
        if (sizeId == null) errors.put("size", "Size không được để trống");
        if (giaBan == null || giaBan.compareTo(BigDecimal.ZERO) < 0)
            errors.put("giaBan", "Giá bán phải lớn hơn hoặc bằng 0");
        if (ton == null || ton < 0) errors.put("ton", "Số lượng tồn phải lớn hơn hoặc bằng 0");

        // Trả về lỗi nếu có
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        // Kiểm tra xem sản phẩm có tồn tại hay không
        SanPham existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm không tồn tại");
        }

        // Cập nhật thông tin sản phẩm
//        existingProduct.setMaSP(maSP);  // Mã sản phẩm có thể được cập nhật nếu cần
        existingProduct.setTenSP(tenSP);
        existingProduct.setGiaBan(giaBan);
        existingProduct.setSoLuongTon(ton);
        existingProduct.setMoTa(moTa);
        existingProduct.setTrangThai(trangThai);

        // Xử lý ảnh (nếu có) - nếu có ảnh mới, cập nhật ảnh
        if (anh != null && !anh.isEmpty()) {
            String imageUrl = uploadImage(anh);
            existingProduct.setAnh(imageUrl);  // Cập nhật ảnh
        }

        // Thiết lập các thuộc tính khóa ngoại từ ID (bạn cần truy vấn các đối tượng từ database)
        existingProduct.setThuongHieu(brandService.getThuongHieuById(thuongHieuId));  // Lấy thương hiệu theo ID
        existingProduct.setMauSac(colorService.findById(mauSacId));  // Lấy màu sắc theo ID
        existingProduct.setChatLieu(materialService.findById(chatLieuId));  // Lấy chất liệu theo ID
        existingProduct.setXuatXu(originService.findById(xuatXuId));  // Lấy xuất xứ theo ID
        existingProduct.setTheLoai(categoryService.findById(theLoaiId));  // Lấy thể loại theo ID
        existingProduct.setSize(sizeService.findById(sizeId));  // Lấy size theo ID

        // Lưu sản phẩm vào database
        SanPham updatedProduct = productService.updateProduct(existingProduct);

        // Trả về kết quả
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    // Thêm mới thương hiệu
    @PostMapping("/brands")
    public ResponseEntity<ThuongHieu> addBrand(@RequestBody ThuongHieu brand) {
        ThuongHieu savedBrand = brandService.add(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
    }

    // Thêm mới màu sắc
    @PostMapping("/colors")
    public ResponseEntity<MauSac> addColor(@RequestBody MauSac color) {
        MauSac savedColor = colorService.add(color);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedColor);
    }

    // Thêm mới chất liệu
    @PostMapping("/materials")
    public ResponseEntity<ChatLieu> addMaterial(@RequestBody ChatLieu material) {
        ChatLieu savedMaterial = materialService.add(material);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMaterial);
    }

    // Thêm mới xuất xứ
    @PostMapping("/origins")
    public ResponseEntity<XuatXu> addOrigin(@RequestBody XuatXu origin) {
        XuatXu savedOrigin = originService.add(origin);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrigin);
    }

    // Thêm mới thể loại
    @PostMapping("/categories")
    public ResponseEntity<TheLoai> addCategory(@RequestBody TheLoai category) {
        TheLoai savedCategory = categoryService.add(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    // Thêm mới size
    @PostMapping("/sizes")
    public ResponseEntity<Size> addSize(@RequestBody Size size) {
        Size savedSize = sizeService.add(size);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSize);
    }

    // Sửa thương hiệu
    @PutMapping("/brands/{id}")
    public ResponseEntity<ThuongHieu> updateBrand(@PathVariable UUID id, @RequestBody ThuongHieu brand) {
        ThuongHieu updatedBrand = brandService.update(id, brand);
        return ResponseEntity.ok(updatedBrand);
    }

    // Sửa màu sắc
    @PutMapping("/colors/{id}")
    public ResponseEntity<MauSac> updateColor(@PathVariable UUID id, @RequestBody MauSac color) {
        MauSac updatedColor = colorService.update(id, color);
        return ResponseEntity.ok(updatedColor);
    }

    // Sửa chất liệu
    @PutMapping("/materials/{id}")
    public ResponseEntity<ChatLieu> updateMaterial(@PathVariable UUID id, @RequestBody ChatLieu material) {
        ChatLieu updatedMaterial = materialService.update(id, material);
        return ResponseEntity.ok(updatedMaterial);
    }

    // Sửa xuất xứ
    @PutMapping("/origins/{id}")
    public ResponseEntity<XuatXu> updateOrigin(@PathVariable UUID id, @RequestBody XuatXu origin) {
        XuatXu updatedOrigin = originService.update(id, origin);
        return ResponseEntity.ok(updatedOrigin);
    }

    // Sửa thể loại
    @PutMapping("/categories/{id}")
    public ResponseEntity<TheLoai> updateCategory(@PathVariable UUID id, @RequestBody TheLoai category) {
        TheLoai updatedCategory = categoryService.update(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    // Sửa size
    @PutMapping("/sizes/{id}")
    public ResponseEntity<Size> updateSize(@PathVariable UUID id, @RequestBody Size size) {
        Size updatedSize = sizeService.update(id, size);
        return ResponseEntity.ok(updatedSize);
    }


    // Xóa thương hiệu
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();  // Trả về HTTP 204 No Content
    }

    // Xóa màu sắc
    @DeleteMapping("/colors/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable UUID id) {
        colorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Xóa chất liệu
    @DeleteMapping("/materials/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable UUID id) {
        materialService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Xóa xuất xứ
    @DeleteMapping("/origins/{id}")
    public ResponseEntity<Void> deleteOrigin(@PathVariable UUID id) {
        originService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Xóa thể loại
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Xóa size
    @DeleteMapping("/sizes/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable UUID id) {
        sizeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
