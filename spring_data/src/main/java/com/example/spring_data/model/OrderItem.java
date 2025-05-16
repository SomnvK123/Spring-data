package com.example.spring_data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_item_id;

    private int order_id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
}