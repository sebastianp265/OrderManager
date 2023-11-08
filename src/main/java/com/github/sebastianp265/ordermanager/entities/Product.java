package com.github.sebastianp265.ordermanager.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

public class Product {

    @GeneratedValue
    @Id
    Long id;

    private String name;

    private BigDecimal price;

    private Long numberAvailableInStock;

}
