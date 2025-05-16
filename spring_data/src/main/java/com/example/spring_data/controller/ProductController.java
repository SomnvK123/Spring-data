package com.example.spring_data.controller;

import com.example.spring_data.model.Product;
import com.example.spring_data.service.ProductService;
import com.example.spring_data.service.ProductServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductServ productServ;

    /**
     * Lấy toàn bộ danh sách sản phẩm.
     */
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        try {
            List<Product> products = productService.findAll();
            List<String> msg = new ArrayList<>();
            msg.add("Get all successful");

            Map<String, Object> response = new HashMap<>();
            response.put("messages", msg);
            response.put("products", products);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Ghi log lỗi hoặc xử lý đặc biệt nếu cần
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lấy top 5 sản phẩm bán chạy nhất.
     */
    @GetMapping("/top5")
    public ResponseEntity<List<Product>> getTop5BestSellingProducts() {
        try {
            List<Product> products = productService.top5BestSellingProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lấy doanh thu theo tên sản phẩm.
     */
    @GetMapping("/revenue/{productName}")
    public ResponseEntity<List<Product>> getRevenueByProduct(@PathVariable String productName) {
        try {
            List<Product> revenues = productService.revenueByProduct(productName);
            return ResponseEntity.ok(revenues);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lấy tổng doanh thu theo tháng.
     */
    @GetMapping("/revenue/month/{month}")
    public ResponseEntity<List<Product>> getTotalRevenueByMonth(@PathVariable String month) {
        try {
            List<Product> revenues = productService.totalRevenueByMonth(month);
            return ResponseEntity.ok(revenues);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lấy toàn bộ danh sách sản phẩm (dùng service khác).
     */
    @GetMapping("/all1")
    public ResponseEntity<List<Product>> getAllProducts1() {
        try {
            List<Product> products = productServ.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Thực hiện chèn hàng loạt sản phẩm.
     */
    @PostMapping("/batch")
    public ResponseEntity<String> batchInsert(@RequestBody List<Product> products) {
        try {
            productService.batchInsert(products);
            String msg = "Insert succesfull";
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            // Ghi log lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // dung de test cho: Thêm Auditing với createdDate, lastModifiedDate (2 trường này cần not null)
    @PostMapping("/insert")
    public ResponseEntity<String> insertProduct(@RequestBody Product product) {
        try{
            productServ.insertProduct(product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity());
            String msg = "Insert succesfull";
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/softDelete/{name}")
    public ResponseEntity<String> softDelete(@PathVariable String name) {
        try{
            productServ.softDelete(name);
            String msg = "Delete succesfull";
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}