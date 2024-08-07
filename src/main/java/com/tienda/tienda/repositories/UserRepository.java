package com.tienda.tienda.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tienda.tienda.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query(value = "SELECT * FROM users WHERE email= ?1 limit 1", nativeQuery = true)
    Optional <User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE username = ?1 limit 1", nativeQuery = true)
    Optional <User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE email = ?1 and roles LIKE '%SELLER%' limit 1", nativeQuery = true)
    Optional <User> findSellerByEmail(String email);
}
