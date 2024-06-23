package com.tienda.tienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.tienda.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
