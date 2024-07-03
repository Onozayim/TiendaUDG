package com.tienda.tienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.tienda.entities.PurchaseRequest;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long> {
    
}
