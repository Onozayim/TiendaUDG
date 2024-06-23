package com.tienda.tienda.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.tienda.entities.User;
import com.tienda.tienda.repositories.UserRepository;
import com.tienda.tienda.services.UserService;
import com.tienda.tienda.utils.auth.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
}
