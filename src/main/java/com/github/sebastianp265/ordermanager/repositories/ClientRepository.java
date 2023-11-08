package com.github.sebastianp265.ordermanager.repositories;

import com.github.sebastianp265.ordermanager.entities.Client;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
