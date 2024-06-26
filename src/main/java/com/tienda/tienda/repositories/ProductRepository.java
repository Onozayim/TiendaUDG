package com.tienda.tienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.tienda.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
