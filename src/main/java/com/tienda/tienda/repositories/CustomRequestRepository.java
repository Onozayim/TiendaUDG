package com.tienda.tienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.tienda.entities.CustomRequest;

public interface CustomRequestRepository extends JpaRepository<CustomRequest, Long> {
    
}
