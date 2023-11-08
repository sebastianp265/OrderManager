package com.github.sebastianp265.ordermanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity(name = "Products")
public class Product {

    @GeneratedValue
    @Id
    Long id;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    private String name;

    private BigDecimal price;

    private Long numberAvailableInStock;

}
