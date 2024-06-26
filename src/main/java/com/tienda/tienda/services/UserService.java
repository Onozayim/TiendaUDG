package com.tienda.tienda.services;


import java.util.Optional;

import com.tienda.tienda.entities.User;

public interface UserService {
    public User createUser(User user);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);
}
