package com.github.sebastianp265.ordermanager.repositories;

import com.github.sebastianp265.ordermanager.entities.Product;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
