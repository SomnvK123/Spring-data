package com.example.spring_data.service;

import com.example.spring_data.model.Product;
import com.example.spring_data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void batchInsert(List<Product> products) {
        productRepository.batchInsert(products);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findById(id);
    }
    public List<Product> top5BestSellingProducts() {
        return productRepository.top5BestSellingProducts();
    }

    public List<Product> revenueByProduct(String productName) {
        return productRepository.revenueByProduct(productName);
    }

    public List<Product> totalRevenueByMonth(String month) {
        return productRepository.totalRevenueByMonth(month);
    }
}
