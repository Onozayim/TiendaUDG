package com.tienda.tienda.services.impls;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.tienda.entities.User;
import com.tienda.tienda.repositories.UserRepository;
import com.tienda.tienda.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
    }


    @Override
    public User findSellerByEmail(String email) {
        return userRepository.findSellerByEmail(email).orElseThrow(() -> new NoSuchElementException("Vendedor no encontrado"));
    }
}
