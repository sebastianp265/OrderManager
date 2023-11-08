package com.github.sebastianp265.ordermanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "Clients")
public class Client {

    @GeneratedValue
    @Id
    private Long id;

    @OneToMany(mappedBy = "client")
    private Set<Order> orders;

    private String name;

    private String surname;

    private String email;
}
