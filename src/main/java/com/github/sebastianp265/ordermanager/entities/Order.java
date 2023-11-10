package com.github.sebastianp265.ordermanager.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Entity(name = "Order_table")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
@ToString
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Set<ProductQuantity> productQuantities;

    private Status status;

    private boolean isCancelled;

    public enum Status {NEW, IN_PROGRESS, DELIVERED}
}


