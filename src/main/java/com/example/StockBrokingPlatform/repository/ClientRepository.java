package com.example.StockBrokingPlatform.repository;

import com.example.StockBrokingPlatform.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Add custom queries here if needed
}
