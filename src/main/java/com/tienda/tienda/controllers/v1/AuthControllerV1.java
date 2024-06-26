package com.tienda.tienda.controllers.v1;

import org.springframework.web.bind.annotation.RestController;

import com.tienda.tienda.entities.User;
import com.tienda.tienda.responses.JsonResponses;
import com.tienda.tienda.services.AuthenticationService;
import com.tienda.tienda.services.JwtService;
import com.tienda.tienda.services.UserService;
import com.tienda.tienda.vars.JWT;
import com.tienda.tienda.vars.params.AuthDTO;
import com.tienda.tienda.vars.params.UserDTO;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "v1/auth")
public class AuthControllerV1 {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    JwtService jwtService;
    @Autowired
    JsonResponses jsonResponses;
    
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postRegister(@Valid @RequestBody UserDTO register) {
        List<String> errors = new ArrayList<>();
        Map<String, List<String>> result = new HashMap<>();

        User user = new User(register);
        
        Optional<User> email = userService.findByEmail(user.getEmail());
        Optional<User> username = userService.findByUsername(user.getUsername());
        
        if(email.isPresent()) 
            errors.add("Este correo ya está en uso");
        if(username.isPresent()) 
            errors.add("Este nombre de usuario ya está en uso");

        if (errors.size() != 0) {
            result.put("errors", errors);   
            return jsonResponses.ReturnErrorData(result, "Error a la hora de registrarese");
        }

        User newUser = authenticationService.signup(user);
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("email", newUser.getEmail());

        return jsonResponses.ReturnOkData(
            new JWT(jwtService.generateToken(extraClaims, newUser)),
            "Usuario registrado"
        );
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postLogin(@Valid @RequestBody AuthDTO params) {
        User authenticatedUser = authenticationService.authenticate(params);

        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("email", authenticatedUser.getEmail());

        return jsonResponses.ReturnOkData(
            new JWT(jwtService.generateToken(extraClaims, authenticatedUser)),
            "Ususario logeado"
        );
    }
}
