package com.tienda.tienda.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tienda.tienda.entities.User;
import com.tienda.tienda.repositories.UserRepository;
import com.tienda.tienda.vars.params.AuthParams;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirm_password(user.getPassword());

        return userRepository.save(user);
    }

    public User authenticate(AuthParams input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}