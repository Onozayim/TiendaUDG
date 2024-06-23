package com.tienda.tienda.controllers.v1;

import org.springframework.web.bind.annotation.RestController;

import com.tienda.tienda.entities.User;
import com.tienda.tienda.services.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping(path = "v1")
public class UserControllerV1 {
    @Autowired
    UserService userService;
    
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public String postMethodName(@Valid @RequestBody User user) {
        // int a = 0;
        // int b = 0;
        // int c = a/b;
        // System.out.println(c);
        System.out.println(user);
        userService.createUser(user);
        return "done";
    }
    
}
