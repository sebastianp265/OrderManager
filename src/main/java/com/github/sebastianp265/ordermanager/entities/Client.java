package com.github.sebastianp265.ordermanager.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Client {

    @GeneratedValue
    @Id
    private Long id;

    private String name;

    private String surname;

    private String email;
}
