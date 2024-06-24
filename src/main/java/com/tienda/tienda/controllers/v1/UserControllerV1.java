package com.tienda.tienda.controllers.v1;

import org.springframework.web.bind.annotation.RestController;

import com.tienda.tienda.entities.User;
import com.tienda.tienda.services.AuthenticationService;
import com.tienda.tienda.services.JwtService;
import com.tienda.tienda.services.UserService;
import com.tienda.tienda.vars.JSONDataObject;
import com.tienda.tienda.vars.JWT;
import com.tienda.tienda.vars.StringConsts;
import com.tienda.tienda.vars.params.AuthParams;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping(path = "v1")
public class UserControllerV1 {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    JwtService jwtService;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping(value = "/auth/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> postRegister(@Valid @RequestBody User user) {
        JSONDataObject jsonObject = new JSONDataObject();
        List<String> errors = new ArrayList<>();
        Map<String, List<String>> result = new HashMap<>();

        Optional<User> email = userService.findByEmail(user.getEmail());
        Optional<User> username = userService.findByUsername(user.getUsername());
        

        System.out.println(email);
        System.out.println(email.isPresent());

        if(email.isPresent()) 
            errors.add("Este correo ya está en uso");
        if(username.isPresent()) 
            errors.add("Este nombre de usuario ya está en uso");

        if (errors.size() != 0) {
            result.put("errors", errors);   
            jsonObject.setData(result);
            jsonObject.setMessage("Error a la hora de registrarse");
            jsonObject.setStatus(StringConsts.Error);

            return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
        }

        jsonObject.setData(new JWT("holaamigos"));
        jsonObject.setMessage("Usuario registrado");
        jsonObject.setStatus(StringConsts.Ok);
        authenticationService.signup(user);
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping(value = "/auth/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> postLogin(@RequestBody AuthParams params) {
        User authenticatedUser = authenticationService.authenticate(params);

        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("email", authenticatedUser.getEmail());

        JSONDataObject jsonObject = new JSONDataObject();

        jsonObject.setData(new JWT(jwtService.generateToken(extraClaims, authenticatedUser)));
        jsonObject.setMessage("Usuario logeado");
        jsonObject.setStatus(StringConsts.Ok);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/user/me")
    public ResponseEntity<?> getME() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        JSONDataObject jsonObject = new JSONDataObject();
        jsonObject.setData(currentUser);
        jsonObject.setMessage("Usuario logeado");
        jsonObject.setStatus(StringConsts.Ok);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
    
    
}
