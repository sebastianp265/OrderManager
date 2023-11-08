package com.github.sebastianp265.ordermanager.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
public class Order {

    @GeneratedValue
    @Id
    private Long id;

    private Client client;

    private List<Product> products;

    enum Status {NEW, IN_PROGRESS, DELIVERED}

    private Status status;
}
