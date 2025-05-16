package com.example.spring_data.repository;

import com.example.spring_data.model.Product;

import java.util.List;

public interface ProductRepository {
    Product findById(int id);
    List<Product> findAll();
    void batchInsert(List<Product> products);

    List<Product> top5BestSellingProducts();

    List<Product> revenueByProduct(String productName);

    List<Product> totalRevenueByMonth(String month);
}
