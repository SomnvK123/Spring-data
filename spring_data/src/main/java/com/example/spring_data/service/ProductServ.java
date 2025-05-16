package com.example.spring_data.service;

import com.example.spring_data.model.Product;
import com.example.spring_data.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServ {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Transactional
    public void insertProduct(String name, String description, double price, int stockQuantity) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        productRepo.save(product);
    }

    @Transactional
    public void softDelete(String name) {
        List<Product> products = productRepo.findByName(name);
        for (Product product : products) {
            product.setDeleted(true);
        }
        productRepo.saveAll(products);
    }
}