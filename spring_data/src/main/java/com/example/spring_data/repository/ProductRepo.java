package com.example.spring_data.repository;

import com.example.spring_data.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.isDeleted  = false")
    List<Product> findAll();

    @Query("SELECT p FROM Product p WHERE p.name = ?1 AND p.isDeleted = false")
    List<Product> findByName(String name);
    
    @Transactional
    @Modifying
    @Query("INSERT INTO Product (name, description, price, stockQuantity) VALUES (?1, ?2, ?3, ?4)")
    void insertProduct(String name, String description, double price, int stockQuantity);

    @Modifying
    @Query("UPDATE Product p SET p.isDeleted = true WHERE p.name = ?1")
    void softDelete(String name);
}